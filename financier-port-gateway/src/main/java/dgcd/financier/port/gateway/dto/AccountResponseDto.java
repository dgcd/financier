package dgcd.financier.port.gateway.dto;

import dgcd.financier.core.domain.CurrencyType;

import java.math.BigDecimal;

public record AccountResponseDto(
        Long id,
        String title,
        CurrencyType currency,
        BigDecimal balance,
        boolean closed
) {}
