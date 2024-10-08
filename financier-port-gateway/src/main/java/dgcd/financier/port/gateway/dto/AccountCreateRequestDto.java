package dgcd.financier.port.gateway.dto;

import dgcd.financier.core.domain.CurrencyType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import static dgcd.financier.core.domain.Constants.ACCOUNT_TITLE_MAX_LENGTH;
import static dgcd.financier.core.domain.Constants.ACCOUNT_TITLE_MIN_LENGTH;

public record AccountCreateRequestDto(
        @NotNull(message = "Title can not be null")
        @Size(
                min = ACCOUNT_TITLE_MIN_LENGTH,
                max = ACCOUNT_TITLE_MAX_LENGTH,
                message = "Account title length must be " + ACCOUNT_TITLE_MIN_LENGTH + ".." + ACCOUNT_TITLE_MAX_LENGTH
        )
        String title,

        @NotNull(message = "Currency can not be null")
        CurrencyType currency
) {}
