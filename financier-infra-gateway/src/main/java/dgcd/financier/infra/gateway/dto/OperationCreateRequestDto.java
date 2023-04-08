package dgcd.financier.infra.gateway.dto;

import dgcd.financier.core.domain.OperationType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public record OperationCreateRequestDto(
        @NotNull(message = "Operation date can not be null")
        LocalDate date,
        @NotNull(message = "Operation id can not be null")
        Long accountId,
        Long accountToId,

        @NotNull(message = "Operation type can not be null")
        OperationType operationType,
        @NotNull(message = "Operation amount can not be null")
        BigDecimal amount,
        BigDecimal amountTo,
        BigDecimal quantity,

        Long subcategoryId,

        // todo: validate boundaries and message for all DTOs
        @Size(min = 1, max = 30, message = "Comment length must be 1..30")
        String comment,
        @Size(min = 1, max = 30, message = "Counterparty length must be 1..30")
        String counterparty
) {}
