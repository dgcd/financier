package dgcd.financier.core.usecase.impl;

import dgcd.financier.core.domain.model.Operation;
import dgcd.financier.core.usecase.api.OperationEditUsecase;
import dgcd.financier.core.usecase.api.dto.common.OperationDto;
import dgcd.financier.core.usecase.api.error.CommonError;
import dgcd.financier.core.usecase.api.port.repository.OperationsRepository;
import dgcd.financier.core.usecase.api.utils.EitherUtils;
import dgcd.financier.core.usecase.impl.error.UsecaseError;
import dgcd.financier.core.usecase.impl.mapper.OperationMapper;
import io.vavr.control.Either;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import static dgcd.financier.core.usecase.api.utils.EitherUtils.toRight;
import static dgcd.financier.core.usecase.impl.error.Errors.OPERATION_NOT_FOUND;
import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;

@RequiredArgsConstructor
public class OperationEditUsecaseImpl implements OperationEditUsecase {

    private final OperationsRepository operationsRepository;


    @Override
    public Either<CommonError, OperationDto> execute(RequestDto request) {
        return toRight(request)
                .map(Context::new)
                .flatMap(this::retrieveOperation)
                .flatMap(this::updateOperation)
                .map(this::mapResponse);
    }


    private Either<CommonError, Context> retrieveOperation(Context context) {
        return toRight(context.getRequest().id())
                .flatMap(this::retrieveOperationInner)
                .map(context::setOperation);
    }


    private Either<CommonError, Operation> retrieveOperationInner(Long operationId) {
        return operationsRepository.findById(operationId)
                .map(EitherUtils::toRight)
                .orElseGet(() -> left(OPERATION_NOT_FOUND));
    }


    private Either<CommonError, Context> updateOperation(Context context) {
        return toRight(context)
                .flatMap(this::updateOperationInner);
    }


    private Either<CommonError, Context> updateOperationInner(Context context) {
        return createOperationUpdateAccount(
                context.getRequest(),
                context.getOperation()
        ).map(context::setOperation);
    }


    private Either<CommonError, Operation> createOperationUpdateAccount(
            RequestDto request,
            Operation operation
    ) {
        try {
            operation
                    .setDate(request.date())
                    .setComment(request.comment())
                    .setCounterparty(request.counterparty())
                    .validate();

            var savedOperation = operationsRepository.update(operation);
            return right(savedOperation);
        } catch (IllegalArgumentException ex) {
            return left(new UsecaseError(ex.getMessage()));
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////


    private OperationDto mapResponse(Context context) {
        return OperationMapper.INSTANCE.fromDomain(context.getOperation());
    }

    //////////////////////////////////////////////////////////////////////////////////////////////


    @Getter
    @Setter
    @Accessors(chain = true)
    @RequiredArgsConstructor
    private static class Context {

        // request
        private final RequestDto request;

        // result
        private Operation operation;

    }

}
