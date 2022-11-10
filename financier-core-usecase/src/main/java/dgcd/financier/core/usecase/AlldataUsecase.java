package dgcd.financier.core.usecase;

import dgcd.financier.core.domain.Account;
import dgcd.financier.core.domain.Category;
import dgcd.financier.core.domain.Currency;
import dgcd.financier.core.domain.Operation;
import dgcd.financier.core.domain.OperationType;
import lombok.NonNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static java.util.Objects.nonNull;

public interface AlldataUsecase<REQ extends Usecase.Request, RESP extends Usecase.Response> extends Usecase<REQ, RESP> {

    @Override
    RESP execute(REQ request);


    record AlldataRows(
            @NonNull List<AccountRow> accounts,
            @NonNull List<CategoryRow> categories,
            @NonNull List<OperationRow> operations
    ) {}


    record AccountRow(
            @NonNull String title,
            @NonNull Currency currency,
            @NonNull BigDecimal balance,
            boolean isClosed
    ) {

        public static AccountRow of(Account account) {
            return new AccountRow(
                    account.getTitle(),
                    account.getCurrency(),
                    account.getBalance(),
                    account.getIsClosed()
            );
        }

    }


    record CategoryRow(
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


    record OperationRow(
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

}
