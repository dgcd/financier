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
        String comment
) {

    public static OperationResponseDto of(Operation operation) {
        var hasCategory = nonNull(operation.getSubcategory());
        var subcat = operation.getSubcategory();
        return new OperationResponseDto(
                operation.getId(),
                operation.getDate(),
                operation.getAccount().getId(),
                operation.getAccount().getTitle(),
                operation.getAccount().getCurrency(),
                operation.getQuantity(),
                operation.getAmount(),
                operation.getType(),

                hasCategory ? subcat.getParent().getId() : null,
                hasCategory ? subcat.getParent().getTitle() : null,
                hasCategory ? subcat.getId() : null,
                hasCategory ? subcat.getTitle() : null,

                operation.getCounterparty(),
                operation.getComment()
        );
    }

}
