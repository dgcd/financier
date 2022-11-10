package dgcd.financier.core.usecase.validator;

import dgcd.financier.core.domain.OperationType;
import dgcd.financier.core.usecase.OperationCreateUsecase;

import java.util.Set;

import static dgcd.financier.core.domain.OperationType.BASE;
import static dgcd.financier.core.domain.OperationType.EXCHANGE;
import static dgcd.financier.core.domain.OperationType.EXPENSE;
import static dgcd.financier.core.domain.OperationType.INCOME;
import static dgcd.financier.core.domain.OperationType.TRANS;
import static java.math.BigDecimal.ZERO;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;

public class OperationCreateUsecaseRequestValidator {

    public static void validateCommon(OperationCreateUsecase.Request request) {
        requireNonNull(request, "Request can not be null");
        requireNonNull(request.getDate(), "Operation date can not be null");
        requireNonNull(request.getAccountIdentity(), "Operation account can not be null");
        requireNonNull(request.getOperationType(), "Operation type can not be null");
        requireNonNull(request.getAmount(), "Operation amount can not be null");
        if (request.getAmount().compareTo(ZERO) == 0) {
            throw new IllegalArgumentException("Operation amount can not be 0");
        }
        // todo more checks
    }

    private static final Set<OperationType> SINGLE_OPS_TYPES = Set.of(INCOME, EXPENSE, BASE);
    private static final Set<OperationType> PAIRED_OPS_TYPES = Set.of(TRANS, EXCHANGE);


    public static void validateSingle(OperationCreateUsecase.Request request) {
        if (!SINGLE_OPS_TYPES.contains(request.getOperationType())) {
            throw new IllegalArgumentException("Wrong type for single operation: " + request.getOperationType());
        }
        if (request.getOperationType() == BASE && nonNull(request.getSubcategoryIdentity())) {
            throw new IllegalArgumentException("Base operation can not have category");
        }
        if (request.getOperationType() != BASE && isNull(request.getSubcategoryIdentity())) {
            throw new IllegalArgumentException("Operation must have category");
        }
        if (nonNull(request.getAccountToIdentity())) {
            throw new IllegalArgumentException("Operation second account must not be set");
        }
        if (isNull(request.getQuantity())) {
            throw new IllegalArgumentException("Operation quantity can not be null");
        }
        if (ZERO.compareTo(request.getQuantity()) >= 0) {
            throw new IllegalArgumentException("Operation quantity must be more then 0");
        }
        // todo more checks
    }


    public static void validatePair(OperationCreateUsecase.Request request) {
        if (!PAIRED_OPS_TYPES.contains(request.getOperationType())) {
            throw new IllegalArgumentException("Wrong type for pair operations: " + request.getOperationType());
        }
        if (isNull(request.getSubcategoryIdentity())) {
            throw new IllegalArgumentException("Pair operations must have category");
        }
        if (isNull(request.getAccountToIdentity())) {
            throw new IllegalArgumentException("Second account id can not be null");
        }
        if (request.getAccountIdentity().equals(request.getAccountToIdentity())) {
            throw new IllegalArgumentException("Account from and account to can not be the same");
        }
        if (nonNull(request.getQuantity())) {
            throw new IllegalArgumentException("Quantity must be null");
        }
        if (TRANS.equals(request.getOperationType()) && nonNull(request.getAmountTo())) {
            throw new IllegalArgumentException("Trans operation can not have second amount");
        }
        if (EXCHANGE.equals(request.getOperationType()) && (isNull(request.getAmountTo()) || request.getAmountTo().compareTo(ZERO) == 0)) {
            throw new IllegalArgumentException("Exchange operation must have second amount");
        }
        // todo more checks
    }

}
