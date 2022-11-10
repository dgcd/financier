package dgcd.financier.core.usecase.impl;

import dgcd.financier.core.domain.Operation;
import dgcd.financier.core.domain.factory.AccountFactory;
import dgcd.financier.core.domain.factory.OperationFactory;
import dgcd.financier.core.usecase.OperationCancelUsecase;
import dgcd.financier.core.usecase.exception.OperationAlreadyCanceledException;
import dgcd.financier.core.usecase.exception.OperationNotExistsException;
import dgcd.financier.core.usecase.port.repository.AccountsRepository;
import dgcd.financier.core.usecase.port.repository.OperationsRepository;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static dgcd.financier.core.usecase.validator.OperationCancelUsecaseRequestValidator.validate;
import static java.util.Objects.isNull;

@RequiredArgsConstructor
public class OperationCancelUsecaseImpl implements OperationCancelUsecase {

    private final AccountsRepository accountsRepository;
    private final OperationsRepository operationsRepository;

    @Override
    public Response execute(Request request) {
        validate(request);

        var operationOpt = operationsRepository.findByIdentity(request.getOperationIdentity());
        var operation = checkOperation(operationOpt, request.getOperationIdentity());
        var corrId = operation.getCorrelationId();

        List<Operation> operations;
        if (isNull(corrId)) {
            operations = List.of(operation);
        } else {
            var prefix = extractCorrelationPrefix(corrId);
            operations = operationsRepository.findByCorrelationIdStartingWith(prefix);
            checkOperations(operations);
        }

        var savedOperations = new ArrayList<Operation>(2);
        for (var op : operations) {
            var account = op.getAccount();
            var newBalance = account.getBalance().subtract(op.getAmount());
            var adjustedAccount = AccountFactory.makeWithNewBalance(account, newBalance);
            var savedAccount = accountsRepository.save(adjustedAccount);

            var newOperation = OperationFactory.makeWithIsCanceledSet(op, savedAccount);
            var savedOperation = operationsRepository.save(newOperation);
            savedOperations.add(savedOperation);
        }

        return new Response(
                savedOperations.stream().map(Operation::getIdentity).toList(),
                savedOperations.stream().map(Operation::getAccount).toList()
        );
    }

    private static String extractCorrelationPrefix(String corrId) {
        return corrId.substring(0, (corrId.length() - 2) - 1);
    }


    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    private static Operation checkOperation(Optional<Operation> operationOptional, Long identity) {
        if (operationOptional.isEmpty()) {
            throw new OperationNotExistsException(identity);
        }
        var operation = operationOptional.get();
        if (operation.getIsCanceled()) {
            throw new OperationAlreadyCanceledException(operation.getIdentity());
        }
        return operation;
    }


    private static void checkOperations(List<Operation> operations) {
        if (operations.size() != 2) {
            throw new IllegalStateException("Wrong number of correlated operations: " + operations.size());
        }
        for (var op : operations) {
            if (op.getIsCanceled()) {
                throw new OperationAlreadyCanceledException(op.getIdentity());
            }
        }
    }

}
