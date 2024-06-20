package dgcd.financier.core.usecase.impl.validator;

import dgcd.financier.core.usecase.api.OperationCreateUsecase.RequestDto;
import dgcd.financier.core.usecase.api.error.CommonError;
import dgcd.financier.core.usecase.impl.error.UsecaseError;
import io.vavr.control.Either;

import static dgcd.financier.core.domain.OperationType.BASE;
import static dgcd.financier.core.domain.OperationType.EXCHANGE;
import static dgcd.financier.core.domain.OperationType.TRANS;
import static dgcd.financier.core.usecase.api.utils.EitherUtils.toRight;
import static io.vavr.control.Either.left;
import static java.math.BigDecimal.ZERO;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public final class OperationCreateUsecaseValidator {

    public static Either<CommonError, RequestDto> validateRequest(RequestDto request) {
        try {
            if (request.operationType().isPaired()) {
                validatePair(request);
            } else {
                validateSingle(request);
            }
            return toRight(request);
        } catch (IllegalArgumentException ex) {
            return left(new UsecaseError(ex.getMessage()));
        }
    }


    private static void validatePair(RequestDto request) {
        if (isNull(request.subcategoryId())) {
            throw new IllegalArgumentException("Pair operations must have category");
        }
        if (isNull(request.accountToId())) {
            throw new IllegalArgumentException("Second account id can not be null");
        }
        if (request.accountId().equals(request.accountToId())) {
            throw new IllegalArgumentException("Account from and account to can not be the same");
        }
        if (nonNull(request.quantity())) {
            throw new IllegalArgumentException("Quantity must be null");
        }
        if (TRANS.equals(request.operationType()) && nonNull(request.amountTo())) {
            throw new IllegalArgumentException("Trans operation can not have second amount");
        }
        if (EXCHANGE.equals(request.operationType()) && (isNull(request.amountTo()) || request.amountTo().compareTo(ZERO) == 0)) {
            throw new IllegalArgumentException("Exchange operation must have second amount");
        }
    }


    private static void validateSingle(RequestDto request) {
        if (request.operationType() == BASE && nonNull(request.subcategoryId())) {
            throw new IllegalArgumentException("Base operation can not have category");
        }
        if (request.operationType() != BASE && isNull(request.subcategoryId())) {
            throw new IllegalArgumentException("Operation must have category");
        }
        if (nonNull(request.accountToId())) {
            throw new IllegalArgumentException("Operation second account must not be set");
        }
        if (isNull(request.quantity())) {
            throw new IllegalArgumentException("Operation quantity can not be null");
        }
        if (ZERO.compareTo(request.quantity()) >= 0) {
            throw new IllegalArgumentException("Operation quantity must be more then 0");
        }
    }

}
