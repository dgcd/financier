package dgcd.financier.core.domain.implementation;

import dgcd.financier.core.domain.Account;
import dgcd.financier.core.domain.Category;
import dgcd.financier.core.domain.Operation;
import dgcd.financier.core.domain.OperationType;
import dgcd.financier.core.domain.validation.OperationValidator;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
public class GeneralOperation implements Operation {

    private final Long identity;
    private final LocalDate date;
    private final Account account;
    private final OperationType type;
    private final BigDecimal amount;
    private final BigDecimal quantity;
    private final Category subcategory;
    private final String comment;
    private final String counterparty;
    private final Boolean isCanceled;
    private final String correlationId;


    public GeneralOperation(
            Long identity,
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
        this.identity = identity;
        this.date = date;
        this.account = account;
        this.type = type;
        this.amount = amount;
        this.quantity = quantity;
        this.subcategory = subcategory;
        this.comment = comment;
        this.counterparty = counterparty;
        this.isCanceled = isCanceled;
        this.correlationId = correlationId;
        OperationValidator.validate(this);
    }

}
