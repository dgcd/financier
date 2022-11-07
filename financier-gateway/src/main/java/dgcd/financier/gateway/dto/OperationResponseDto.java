package dgcd.financier.gateway.dto;

import dgcd.financier.core.domain.Currency;
import dgcd.financier.core.domain.OperationType;

import java.math.BigDecimal;
import java.time.LocalDate;

public record OperationResponseDto(
        Long id,
        LocalDate date,

        Long accountId,
        String accountTitle,
        Currency currency,

        OperationType type,
        BigDecimal quantity,
        BigDecimal amount,

        Long categoryId,
        String categoryTitle,
        Long subcategoryId,
        String subcategoryTitle,

        String counterparty,
        String comment,

        String correlationId
) {}
