package dgcd.financier.port.gateway.dto;

import dgcd.financier.core.domain.OperationType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

import static dgcd.financier.core.domain.Constants.OPERATION_COMMENT_MAX_LENGTH;
import static dgcd.financier.core.domain.Constants.OPERATION_COMMENT_MIN_LENGTH;
import static dgcd.financier.core.domain.Constants.OPERATION_COUNTERPARTY_MAX_LENGTH;
import static dgcd.financier.core.domain.Constants.OPERATION_COUNTERPARTY_MIN_LENGTH;

public record OperationCreateRequestDto(
        @NotNull(message = "Operation date can not be null")
        LocalDate date,
        @Min(1)
        @NotNull(message = "Operation id can not be null")
        Long accountId,
        @Min(1)
        Long accountToId,

        @NotNull(message = "Operation type can not be null")
        OperationType operationType,

        @Positive
        BigDecimal quantity,
        @NotNull(message = "Operation amount can not be null")
        BigDecimal amount,
        BigDecimal amountTo,

        @Min(1)
        Long subcategoryId,

        @Size(
                min = OPERATION_COMMENT_MIN_LENGTH,
                max = OPERATION_COMMENT_MAX_LENGTH,
                message = "Comment length must be " + OPERATION_COMMENT_MIN_LENGTH + ".." + OPERATION_COMMENT_MAX_LENGTH
        )
        String comment,
        @Size(
                min = OPERATION_COUNTERPARTY_MIN_LENGTH,
                max = OPERATION_COUNTERPARTY_MAX_LENGTH,
                message = "Counterparty length must be " + OPERATION_COUNTERPARTY_MIN_LENGTH + ".." + OPERATION_COUNTERPARTY_MAX_LENGTH
        )
        String counterparty
) {}
