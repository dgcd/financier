package dgcd.financier.core.api.dto;

import dgcd.financier.core.domain.CurrencyType;

public record AccountCreateRequestDto(
        String title,
        CurrencyType currency
) {}
