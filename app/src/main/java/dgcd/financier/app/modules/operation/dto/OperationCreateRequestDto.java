package dgcd.financier.app.modules.operation.dto;

import dgcd.financier.app.dictionary.OperationType;
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
        BigDecimal amountTo,
        BigDecimal quantity,

        Long subcategoryId,

        @Size(min = 1, max = 30, message = "Comment length must be 1..30")
        String comment,
        @Size(min = 1, max = 30, message = "Counterparty length must be 1..30")
        String counterparty
) {}
