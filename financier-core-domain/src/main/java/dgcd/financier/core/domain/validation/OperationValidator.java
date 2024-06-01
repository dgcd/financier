package dgcd.financier.core.domain.validation;

import dgcd.financier.core.domain.model.Operation;

import static dgcd.financier.core.domain.Constants.OPERATION_COMMENT_MAX_LENGTH;
import static dgcd.financier.core.domain.Constants.OPERATION_COMMENT_MIN_LENGTH;
import static dgcd.financier.core.domain.Constants.OPERATION_CORRELATION_MAX_LENGTH;
import static dgcd.financier.core.domain.Constants.OPERATION_CORRELATION_MIN_LENGTH;
import static dgcd.financier.core.domain.Constants.OPERATION_COUNTERPARTY_MAX_LENGTH;
import static dgcd.financier.core.domain.Constants.OPERATION_COUNTERPARTY_MIN_LENGTH;
import static dgcd.financier.core.domain.OperationType.BASE;
import static dgcd.financier.core.domain.validation.ValidationUtils.checkGreaterThenZero;
import static dgcd.financier.core.domain.validation.ValidationUtils.checkId;
import static dgcd.financier.core.domain.validation.ValidationUtils.checkLength;
import static dgcd.financier.core.domain.validation.ValidationUtils.checkNonNull;
import static java.util.Objects.nonNull;

public class OperationValidator {

    public static void validate(Operation operation) {
        checkId(operation.getId(), "id");

        checkNonNull(operation.getDate(), "date");

        checkNonNull(operation.getAccountId(), "accountId");
        checkId(operation.getAccountId(), "accountId");

        checkNonNull(operation.getType(), "type");
        checkNonNull(operation.getAmount(), "amount");

        checkNonNull(operation.getQuantity(), "quantity");
        checkGreaterThenZero(operation.getQuantity(), "quantity");

        // todo: check this later
        if (BASE.equals(operation.getType())) {
            if (nonNull(operation.getSubcategoryId())) {
                throw new IllegalArgumentException("BASE operation can not have category");
            }
        } else {
            checkNonNull(operation.getSubcategoryId(), "Non-BASE operation must have category");
            if (operation.getSubcategory().isParent()) {
                throw new IllegalArgumentException("Subcategory can not be parent");
            }
        }

        checkLength(operation.getComment(), OPERATION_COMMENT_MIN_LENGTH, OPERATION_COMMENT_MAX_LENGTH, "comment");

        checkLength(operation.getCounterparty(), OPERATION_COUNTERPARTY_MIN_LENGTH, OPERATION_COUNTERPARTY_MAX_LENGTH, "counterparty");

        checkLength(operation.getCorrelationId(), OPERATION_CORRELATION_MIN_LENGTH, OPERATION_CORRELATION_MAX_LENGTH, "correlationId");
    }

}
