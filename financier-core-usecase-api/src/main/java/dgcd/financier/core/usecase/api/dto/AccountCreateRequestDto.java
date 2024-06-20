package dgcd.financier.core.usecase.api.dto;

import dgcd.financier.core.domain.CurrencyType;

//todo: убрать в интерфейсы
public record AccountCreateRequestDto(
        String title,
        CurrencyType currency
) {}
