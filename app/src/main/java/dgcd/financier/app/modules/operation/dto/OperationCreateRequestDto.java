package dgcd.financier.app.modules.operation.dto;

import dgcd.financier.app.dictionary.OperationType;
import dgcd.financier.app.modules.operation.Operation;

import java.math.BigDecimal;
import java.time.LocalDate;

public record OperationCreateRequestDto(
        LocalDate date,
        Long accountId,
        Long accountToId,
        BigDecimal amount,
        BigDecimal quantity,
        OperationType operationType,
//        Long categoryId,
        String counterparty,
        String comment
) {

    public Operation makeOperation() {
        return new Operation(
                null,
                date,
                null,
                quantity,
                amount,
                operationType,
                null,
                counterparty,
                comment
        );
    }

}
