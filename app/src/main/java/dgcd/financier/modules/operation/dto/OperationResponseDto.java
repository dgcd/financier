package dgcd.financier.modules.operation.dto;

import dgcd.financier.domain.dictionary.Currency;
import dgcd.financier.domain.dictionary.OperationType;

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
        String comment
) {

//    public static OperationResponseDto of(Operation operation) {
//        var subcat = operation.getSubcategory();
//        var hasSubcat = nonNull(subcat);
//        var account = operation.getAccount();
//        return new OperationResponseDto(
//                operation.getId(),
//                operation.getDate(),
//
//                account.getId(),
//                account.getTitle(),
//                account.getCurrency(),
//
//                operation.getQuantity(),
//                operation.getAmount(),
//                operation.getType(),
//
//                hasSubcat ? subcat.getParent().getId() : null,
//                hasSubcat ? subcat.getParent().getTitle() : null,
//                hasSubcat ? subcat.getId() : null,
//                hasSubcat ? subcat.getTitle() : null,
//
//                operation.getCounterparty(),
//                operation.getComment()
//        );
//    }

}
