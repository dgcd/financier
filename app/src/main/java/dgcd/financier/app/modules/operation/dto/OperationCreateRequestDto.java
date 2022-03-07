package dgcd.financier.app.modules.operation.dto;

import dgcd.financier.app.dictionary.OperationType;
import dgcd.financier.app.modules.operation.Operation;

import java.math.BigDecimal;
import java.time.LocalDate;

public record OperationCreateRequestDto(
        LocalDate date,
        Long accountId,
        Long accountToId,

        OperationType operationType,
        BigDecimal amount,
        BigDecimal quantity,

        Long subcategoryId,

        String comment,
        String counterparty
) {

    public Operation makeOperation() {
        return new Operation(
                null,
                date,
                null,
                operationType,
                amount,
                quantity,
                null,
                comment,
                counterparty
        );
    }

}
