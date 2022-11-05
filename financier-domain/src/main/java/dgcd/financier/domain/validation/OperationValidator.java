package dgcd.financier.domain.validation;

import dgcd.financier.domain.Operation;
import dgcd.financier.domain.exception.IllegalOperationCommentException;
import dgcd.financier.domain.exception.IllegalOperationCorrelationException;
import dgcd.financier.domain.exception.IllegalOperationCounterpartyException;

import static dgcd.financier.domain.Constants.OPERATION_COMMENT_MAX_LENGTH;
import static dgcd.financier.domain.Constants.OPERATION_COMMENT_MIN_LENGTH;
import static dgcd.financier.domain.Constants.OPERATION_CORRELATION_MAX_LENGTH;
import static dgcd.financier.domain.Constants.OPERATION_CORRELATION_MIN_LENGTH;
import static dgcd.financier.domain.Constants.OPERATION_COUNTERPARTY_MAX_LENGTH;
import static dgcd.financier.domain.Constants.OPERATION_COUNTERPARTY_MIN_LENGTH;
import static dgcd.financier.domain.validation.ValidationUtils.checkStringBoundaries;
import static java.math.BigDecimal.ZERO;
import static java.util.Objects.requireNonNull;

public class OperationValidator {

    public static void validate(Operation operation) {
        requireNonNull(operation.getDate(), "Operation date can not be null");
        requireNonNull(operation.getAccount(), "Operation account can not be null");
        requireNonNull(operation.getType(), "Operation type can not be null");
        requireNonNull(operation.getAmount(), "Operation amount can not be null");

        requireNonNull(operation.getQuantity(), "Operation quantity can not be null");
        if (operation.getQuantity().compareTo(ZERO) <= 0) {
            throw new IllegalArgumentException("Quantity must be greater the 0");
        }

        requireNonNull(operation.getSubcategory(), "Operation subcategory can not be null");
        if (operation.getSubcategory().isParent()) {
            throw new IllegalArgumentException("Subcategory can not be parent");
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

        requireNonNull(operation.getIsCanceled(), "Operation isCanceled statis can not be null");

        var correlation = operation.getCorrelationId();
        checkStringBoundaries(
                correlation,
                OPERATION_CORRELATION_MIN_LENGTH,
                OPERATION_CORRELATION_MAX_LENGTH,
                IllegalOperationCorrelationException::new
        );
    }

}
