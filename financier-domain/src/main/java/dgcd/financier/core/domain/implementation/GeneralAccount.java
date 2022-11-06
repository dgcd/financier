package dgcd.financier.core.domain.implementation;

import dgcd.financier.core.domain.Account;
import dgcd.financier.core.domain.Currency;
import dgcd.financier.core.domain.validation.AccountValidator;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@ToString
public final class GeneralAccount implements Account {

    private final Long identity;
    private final String title;
    private final Currency currency;
    private final BigDecimal balance;
    private final Boolean isClosed;

    public GeneralAccount(
            Long identity,
            String title,
            Currency currency,
            BigDecimal balance,
            Boolean isClosed
    ) {
        this.identity = identity;
        this.title = title;
        this.currency = currency;
        this.balance = balance;
        this.isClosed = isClosed;
        AccountValidator.validate(this);
    }

}
