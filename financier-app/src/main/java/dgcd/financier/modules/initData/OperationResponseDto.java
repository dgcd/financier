package dgcd.financier.modules.initData;

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

        BigDecimal quantity,
        BigDecimal amount,
        OperationType type,

        Long categoryId,
        String categoryTitle,
        Long subcategoryId,
        String subcategoryTitle,

        String counterparty,
        String comment,

        String correlationId
) {
}
