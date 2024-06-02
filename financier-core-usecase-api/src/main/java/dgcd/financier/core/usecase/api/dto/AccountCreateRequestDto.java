package dgcd.financier.core.usecase.api.dto;

import dgcd.financier.core.domain.CurrencyType;

public record AccountCreateRequestDto(
        String title,
        CurrencyType currency
) {}
