package dgcd.financier.infra.gateway.dto;

import dgcd.financier.core.domain.Currency;

import java.math.BigDecimal;

public record AccountResponseDto(
        Long id,
        String title,
        Currency currency,
        BigDecimal balance,
        Boolean isClosed
) {}
