package dgcd.financier.core.usecase.api.dto.common;

import dgcd.financier.core.domain.CurrencyType;
import dgcd.financier.core.domain.OperationType;

import java.math.BigDecimal;
import java.time.LocalDate;

public record OperationDto(
        Long id,
        LocalDate date,

        Long accountId,
        String accountTitle,
        CurrencyType currency,

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
