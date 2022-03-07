package dgcd.financier.app.modules.operation.dto;

import dgcd.financier.app.dictionary.OperationType;
import dgcd.financier.app.modules.operation.Operation;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public record OperationCreateRequestDto(
        @NotNull
        LocalDate date,
        @NotNull
        Long accountId,
        Long accountToId,

        @NotNull
        OperationType operationType,
        @NotNull
        BigDecimal amount,
        BigDecimal quantity,

        Long subcategoryId,

        @Size(min = 1, max = 30, message = "Comment length must be 1..30")
        String comment,
        @Size(min = 1, max = 30, message = "Counterparty length must be 1..30")
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
