package dgcd.financier.core.domain.validation;

import dgcd.financier.core.domain.Operation;
import dgcd.financier.core.domain.exception.IllegalOperationCommentException;
import dgcd.financier.core.domain.exception.IllegalOperationCorrelationException;
import dgcd.financier.core.domain.exception.IllegalOperationCounterpartyException;

import static dgcd.financier.core.domain.Constants.OPERATION_COMMENT_MAX_LENGTH;
import static dgcd.financier.core.domain.Constants.OPERATION_COMMENT_MIN_LENGTH;
import static dgcd.financier.core.domain.Constants.OPERATION_CORRELATION_MAX_LENGTH;
import static dgcd.financier.core.domain.Constants.OPERATION_CORRELATION_MIN_LENGTH;
import static dgcd.financier.core.domain.Constants.OPERATION_COUNTERPARTY_MAX_LENGTH;
import static dgcd.financier.core.domain.Constants.OPERATION_COUNTERPARTY_MIN_LENGTH;
import static dgcd.financier.core.domain.OperationType.BASE;
import static dgcd.financier.core.domain.validation.ValidationUtils.checkIdentity;
import static dgcd.financier.core.domain.validation.ValidationUtils.checkStringBoundaries;
import static java.math.BigDecimal.ZERO;
import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;

public class OperationValidator {

    public static void validate(Operation operation) {
        checkIdentity(operation.getIdentity());

        requireNonNull(operation.getDate(), "Operation date can not be null");
        requireNonNull(operation.getAccount(), "Operation account can not be null");
        requireNonNull(operation.getType(), "Operation type can not be null");
        requireNonNull(operation.getAmount(), "Operation amount can not be null");

        requireNonNull(operation.getQuantity(), "Operation quantity can not be null");
        if (operation.getQuantity().compareTo(ZERO) <= 0) {
            throw new IllegalArgumentException("Quantity must be greater the 0");
        }

        if (BASE.equals(operation.getType())) {
            if (nonNull(operation.getSubcategory())) {
                throw new IllegalArgumentException("BASE operation can not have category");
            }
        } else {
            requireNonNull(operation.getSubcategory(), "Non-BASE operation subcategory can not be null");
            if (operation.getSubcategory().isParent()) {
                throw new IllegalArgumentException("Subcategory can not be parent");
            }
        }

        var comment = operation.getComment();
        checkStringBoundaries(
                comment,
                OPERATION_COMMENT_MIN_LENGTH,
                OPERATION_COMMENT_MAX_LENGTH,
                IllegalOperationCommentException::new
        );

        var counterparty = operation.getCounterparty();
        checkStringBoundaries(
                counterparty,
                OPERATION_COUNTERPARTY_MIN_LENGTH,
                OPERATION_COUNTERPARTY_MAX_LENGTH,
                IllegalOperationCounterpartyException::new
        );

        requireNonNull(operation.getIsCanceled(), "Operation isCanceled status can not be null");

        var correlation = operation.getCorrelationId();
        checkStringBoundaries(
                correlation,
                OPERATION_CORRELATION_MIN_LENGTH,
                OPERATION_CORRELATION_MAX_LENGTH,
                IllegalOperationCorrelationException::new
        );
    }

}
