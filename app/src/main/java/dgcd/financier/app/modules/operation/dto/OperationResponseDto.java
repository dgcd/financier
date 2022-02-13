package dgcd.financier.app.modules.operation.dto;

import dgcd.financier.app.dictionary.Currency;
import dgcd.financier.app.dictionary.OperationType;
import dgcd.financier.app.modules.operation.Operation;

import java.math.BigDecimal;
import java.time.LocalDate;

import static java.util.Objects.nonNull;

public record OperationResponseDto(
        Long id,
        LocalDate date,
        Long accountId,
        Currency currency,
        BigDecimal quantity,
        BigDecimal amount,
        OperationType type,
        String group,
        String category,
        String counterparty,
        String comment
) {

    public static OperationResponseDto of(Operation operation) {
        var hasCategory = nonNull(operation.getCategory());
        return new OperationResponseDto(
                operation.getId(),
                operation.getDate(),
                operation.getAccount().getId(),
                operation.getAccount().getCurrency(),
                operation.getQuantity(),
                operation.getAmount(),
                operation.getType(),
                hasCategory ? operation.getCategory().getParent().getTitle() : null,
                hasCategory ? operation.getCategory().getTitle() : null,
                operation.getCounterparty(),
                operation.getComment()
        );
    }

}
