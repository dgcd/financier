package dgcd.financier.domain.implementation;

import dgcd.financier.domain.Account;
import dgcd.financier.domain.Currency;
import dgcd.financier.domain.validation.AccountValidator;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public final class GeneralAccount implements Account {

    private final Long id;
    private final String title;
    private final Currency currency;
    private final BigDecimal balance;
    private final Boolean isClosed;

    public GeneralAccount(
            Long id,
            String title,
            Currency currency,
            BigDecimal balance,
            Boolean isClosed
    ) {
        this.id = id;
        this.title = title;
        this.currency = currency;
        this.balance = balance;
        this.isClosed = isClosed;
        AccountValidator.validate(this);
    }

}
