package dgcd.financier.domain.implementation;

import dgcd.financier.domain.Account;
import dgcd.financier.domain.Category;
import dgcd.financier.domain.Operation;
import dgcd.financier.domain.OperationType;
import dgcd.financier.domain.validation.OperationValidator;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
public class GeneralOperation implements Operation {

    private final Long id;
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
        this.id = id;
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
