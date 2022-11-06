package dgcd.financier.core.domain.factory;

import dgcd.financier.core.domain.Account;
import dgcd.financier.core.domain.Category;
import dgcd.financier.core.domain.Operation;
import dgcd.financier.core.domain.OperationType;
import dgcd.financier.core.domain.implementation.GeneralOperation;

import java.math.BigDecimal;
import java.time.LocalDate;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class OperationFactory {

    public static Operation makeNew(
            LocalDate date,
            Account account,
            OperationType type,
            BigDecimal amount,
            BigDecimal quantity,
            Category subcategory,
            String comment,
            String counterparty,
            String correlationId
    ) {
        return new GeneralOperation(
                null,
                date,
                account,
                type,
                amount,
                quantity,
                subcategory,
                comment,
                counterparty,
                FALSE,
                correlationId
        );
    }


    public static Operation makeExisting(
            Long id,
            LocalDate date,
            Account account,
            OperationType type,
            BigDecimal amount,
            BigDecimal quantity,
            Category subcategory,
            String comment,
            String counterparty,
            Boolean isCanceled,
            String correlationId
    ) {
        return new GeneralOperation(
                id,
                date,
                account,
                type,
                amount,
                quantity,
                subcategory,
                comment,
                counterparty,
                isCanceled,
                correlationId
        );
    }


    public static Operation makeWithIsCanceledSet(Operation source, Account updatedAccount) {
        return new GeneralOperation(
                source.getIdentity(),
                source.getDate(),
                updatedAccount,
                source.getType(),
                source.getAmount(),
                source.getQuantity(),
                source.getSubcategory(),
                source.getComment(),
                source.getCounterparty(),
                TRUE,
                source.getCorrelationId()
        );
    }

}
