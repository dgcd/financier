package dgcd.financier.core.usecase.impl;

import dgcd.financier.core.domain.model.Operation;
import dgcd.financier.core.usecase.api.OperationCancelUsecase;
import dgcd.financier.core.usecase.api.error.CommonError;
import dgcd.financier.core.usecase.api.port.repository.AccountsRepository;
import dgcd.financier.core.usecase.api.port.repository.OperationsRepository;
import dgcd.financier.core.usecase.api.utils.EitherUtils;
import dgcd.financier.core.usecase.impl.mapper.AccountMapper;
import io.vavr.control.Either;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static dgcd.financier.core.usecase.api.utils.EitherUtils.toRight;
import static dgcd.financier.core.usecase.impl.error.Errors.ACCOUNT_ALREADY_CLOSED;
import static dgcd.financier.core.usecase.impl.error.Errors.OPERATION_ALREADY_CANCELED;
import static dgcd.financier.core.usecase.impl.error.Errors.OPERATION_NOT_FOUND;
import static dgcd.financier.core.usecase.impl.error.Errors.TOO_MANY_OPERATIONS_FOUND;
import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;

@RequiredArgsConstructor
public class OperationCancelUsecaseImpl implements OperationCancelUsecase {

    private final AccountsRepository accountsRepository;
    private final OperationsRepository operationsRepository;


    @Override
    public Either<CommonError, ResponseDto> execute(Long operationId) {
        return toRight(operationId)
                .map(Context::new)
                .flatMap(this::findOperation)
                .flatMap(this::extractCorrelationIfNeeded)
                .flatMap(this::findAllOperations)
                .flatMap(this::checkAllOperations)
                .flatMap(this::processAllOperations)
                .map(this::mapResponse);
    }


    private Either<CommonError, Context> findOperation(Context context) {
        return toRight(context.getOperationId())
                .map(operationsRepository::findById)
                .flatMap(this::checkOperation)
                .map(context::setOperation);
    }


    private Either<CommonError, Operation> checkOperation(Optional<Operation> operationOpt) {
        return operationOpt
                .map(EitherUtils::toRight)
                .orElseGet(() -> left(OPERATION_NOT_FOUND));
    }


    private Either<CommonError, Context> extractCorrelationIfNeeded(Context context) {
        var corrId = context.getOperation().getCorrelationId();
        if (corrId != null) {
            var prefix = corrId.substring(0, (corrId.length() - 2) - 1);
            context.setCorrelationPrefix(prefix);
        }
        return right(context);
    }


    private Either<CommonError, Context> findAllOperations(Context context) {
        var prefix = context.getCorrelationPrefix();
        if (prefix == null) {
            context.getOperations().add(context.getOperation());
        } else {
            var operations = operationsRepository.findByCorrelationIdStartingWith(prefix);
            if (operations.size() != 2) {
                return left(TOO_MANY_OPERATIONS_FOUND);
            }
            context.getOperations().addAll(operations);
        }
        return right(context);
    }


    private Either<CommonError, Context> checkAllOperations(Context context) {
        for (var operation : context.getOperations()) {
            if (operation.isCanceled()) {
                return left(OPERATION_ALREADY_CANCELED);
            }
            if (operation.getAccount().isClosed()) {
                return left(ACCOUNT_ALREADY_CLOSED);
            }
        }
        return right(context);
    }


    private Either<CommonError, Context> processAllOperations(Context context) {
        for (var operation : context.getOperations()) {
            var account = operation.getAccount();
            var balance = account.getBalance().subtract(operation.getAmount());
            account.setBalance(balance).validate();
            accountsRepository.update(account);

            operation.setCanceled(true).validate();
            var savedOperation = operationsRepository.update(operation);
            context.getResultOperations().add(savedOperation);
        }
        return right(context);
    }


    private ResponseDto mapResponse(Context context) {
        var operationIds = context.getOperations().stream()
                .map(Operation::getId)
                .toList();
        var accounts = context.getOperations().stream()
                .map(Operation::getAccount)
                .map(AccountMapper.INSTANCE::fromDomain)
                .toList();
        return new ResponseDto(operationIds, accounts);
    }


    @Getter
    @Setter
    @Accessors(chain = true)
    @RequiredArgsConstructor
    private static class Context {

        // request
        private final Long operationId;

        // intermediate
        private Operation operation;
        private String correlationPrefix;
        private final List<Operation> operations = new ArrayList<>();

        // result
        private final List<Operation> resultOperations = new ArrayList<>();

    }

}
