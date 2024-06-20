package dgcd.financier.core.domain.validation;

import dgcd.financier.core.domain.model.Operation;

import static dgcd.financier.core.domain.Constants.AMOUNT_SCALE;
import static dgcd.financier.core.domain.Constants.OPERATION_COMMENT_MAX_LENGTH;
import static dgcd.financier.core.domain.Constants.OPERATION_COMMENT_MIN_LENGTH;
import static dgcd.financier.core.domain.Constants.OPERATION_CORRELATION_MAX_LENGTH;
import static dgcd.financier.core.domain.Constants.OPERATION_CORRELATION_MIN_LENGTH;
import static dgcd.financier.core.domain.Constants.OPERATION_COUNTERPARTY_MAX_LENGTH;
import static dgcd.financier.core.domain.Constants.OPERATION_COUNTERPARTY_MIN_LENGTH;
import static dgcd.financier.core.domain.Constants.QUANTITY_SCALE;
import static dgcd.financier.core.domain.OperationType.BASE;
import static dgcd.financier.core.domain.validation.ValidationUtils.checkId;
import static dgcd.financier.core.domain.validation.ValidationUtils.checkLength;
import static dgcd.financier.core.domain.validation.ValidationUtils.checkMaxScale;
import static dgcd.financier.core.domain.validation.ValidationUtils.checkMustBeNull;
import static dgcd.financier.core.domain.validation.ValidationUtils.checkNonNull;
import static dgcd.financier.core.domain.validation.ValidationUtils.checkNotZero;
import static dgcd.financier.core.domain.validation.ValidationUtils.checkPositive;

public class OperationValidator {

    public static Operation validate(Operation operation) {
        checkId(operation.getId(), "id");

        checkNonNull(operation.getDate(), "date");

        checkNonNull(operation.getAccountId(), "accountId");
        checkId(operation.getAccountId(), "accountId");

        checkNonNull(operation.getType(), "type");

        checkNonNull(operation.getQuantity(), "quantity");
        checkPositive(operation.getQuantity(), "quantity");
        checkMaxScale(operation.getQuantity(), QUANTITY_SCALE, "quantity");

        checkNonNull(operation.getAmount(), "amount");
        checkNotZero(operation.getAmount(), "amount");
        checkMaxScale(operation.getAmount(), AMOUNT_SCALE, "amount");

        if (BASE.equals(operation.getType())) {
            checkMustBeNull(operation.getSubcategoryId(), "subcategoryId");
        } else {
            checkNonNull(operation.getSubcategoryId(), "subcategoryId");
        }

        checkLength(operation.getComment(), OPERATION_COMMENT_MIN_LENGTH, OPERATION_COMMENT_MAX_LENGTH, "comment");
        checkLength(operation.getCounterparty(), OPERATION_COUNTERPARTY_MIN_LENGTH, OPERATION_COUNTERPARTY_MAX_LENGTH, "counterparty");

        if (operation.getType().isPaired()) {
            checkNonNull(operation.getCorrelationId(), "correlationId");
            checkLength(operation.getCorrelationId(), OPERATION_CORRELATION_MIN_LENGTH, OPERATION_CORRELATION_MAX_LENGTH, "correlationId");
        } else {
            checkMustBeNull(operation.getCorrelationId(), "correlationId");
        }

        return operation;
    }

}
