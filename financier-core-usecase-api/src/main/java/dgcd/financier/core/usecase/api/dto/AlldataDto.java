package dgcd.financier.core.usecase.api.dto;

import dgcd.financier.core.domain.CurrencyType;
import dgcd.financier.core.domain.OperationType;
import dgcd.financier.core.domain.model.Account;
import dgcd.financier.core.domain.model.Category;
import dgcd.financier.core.domain.model.Operation;
import dgcd.financier.core.domain.model.Rate;
import lombok.NonNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static java.util.Objects.nonNull;

public record AlldataDto(
        @NonNull List<AccountRow> accounts,
        @NonNull List<CategoryRow> categories,
        @NonNull List<OperationRow> operations,
        @NonNull List<RateRow> rates
) {

    public record AccountRow(
            @NonNull String title,
            @NonNull CurrencyType currency,
            @NonNull BigDecimal balance,
            boolean isClosed
    ) {

        public static AccountRow of(Account account) {
            return new AccountRow(
                    account.getTitle(),
                    account.getCurrency(),
                    account.getBalance(),
                    account.isClosed()
            );
        }

    }


    public record CategoryRow(
            @NonNull String parentTitle,
            @NonNull String ownTitle
    ) {

        public static CategoryRow of(Category category) {
            return new CategoryRow(
                    category.getParent().getTitle(),
                    category.getTitle()
            );
        }

    }


    public record OperationRow(
            @NonNull LocalDate date,
            @NonNull String accountTitle,
            @NonNull BigDecimal amount,
            @NonNull BigDecimal quantity,
            @NonNull OperationType type,
            String parentCategoryTitle,
            String subcategoryTitle,
            String comment,
            String counterparty,
            String correlationId
    ) {

        public static OperationRow of(Operation operation) {
            boolean hasCategory = nonNull(operation.getSubcategory());
            return new OperationRow(
                    operation.getDate(),
                    operation.getAccount().getTitle(),
                    operation.getAmount(),
                    operation.getQuantity(),
                    operation.getType(),
                    hasCategory ? operation.getSubcategory().getParent().getTitle() : null,
                    hasCategory ? operation.getSubcategory().getTitle() : null,
                    operation.getComment(),
                    operation.getCounterparty(),
                    operation.getCorrelationId()
            );
        }

    }


    public record RateRow(
            @NonNull LocalDate date,
            @NonNull BigDecimal eurRate,
            @NonNull BigDecimal usdRate

    ) {

        public static RateRow of(Rate rates) {
            return new RateRow(
                    rates.getDate(),
                    rates.getEur(),
                    rates.getUsd()
            );
        }

    }

}
