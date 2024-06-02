package dgcd.financier.core.usecase.api.dto.common;

import dgcd.financier.core.domain.CurrencyType;

import java.math.BigDecimal;

public record AccountDto(
        Long id,
        String title,
        CurrencyType currency,
        BigDecimal balance,
        boolean closed
) {}
