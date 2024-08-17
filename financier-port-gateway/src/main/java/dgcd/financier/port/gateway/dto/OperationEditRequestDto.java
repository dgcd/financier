package dgcd.financier.port.gateway.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

import static dgcd.financier.core.domain.Constants.OPERATION_COMMENT_MAX_LENGTH;
import static dgcd.financier.core.domain.Constants.OPERATION_COMMENT_MIN_LENGTH;
import static dgcd.financier.core.domain.Constants.OPERATION_COUNTERPARTY_MAX_LENGTH;
import static dgcd.financier.core.domain.Constants.OPERATION_COUNTERPARTY_MIN_LENGTH;

public record OperationEditRequestDto(
        @NotNull(message = "Operation id can not be null")
        @Min(1)
        Long id,

        @NotNull(message = "Operation date can not be null")
        LocalDate date,

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
