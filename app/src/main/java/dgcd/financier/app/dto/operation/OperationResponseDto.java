package dgcd.financier.app.dto.operation;

import dgcd.financier.app.domain.dict.Currency;
import dgcd.financier.app.domain.dict.OperationType;
import dgcd.financier.app.domain.model.Operation;

import java.math.BigDecimal;
import java.time.LocalDate;

import static java.util.Objects.nonNull;

public record OperationResponseDto(
        Long id,
        LocalDate date,
        Currency currency,
        BigDecimal amount,
        Float quantity,
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
                operation.getAccount().getCurrency(),
                operation.getAmount(),
                operation.getQuantity(),
                operation.getType(),
                hasCategory ? operation.getCategory().getParent().getTitle() : null,
                hasCategory ? operation.getCategory().getTitle() : null,
                operation.getCounterparty(),
                operation.getComment()
        );
    }

}
