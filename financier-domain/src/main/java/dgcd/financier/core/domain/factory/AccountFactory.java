package dgcd.financier.core.domain.factory;

import dgcd.financier.core.domain.Account;
import dgcd.financier.core.domain.Currency;
import dgcd.financier.core.domain.implementation.GeneralAccount;

import java.math.BigDecimal;

import static java.lang.Boolean.FALSE;
import static java.math.BigDecimal.ZERO;

public class AccountFactory {

    public static Account makeNew(String title, Currency currency) {
        return new GeneralAccount(
                null,
                title,
                currency,
                ZERO,
                FALSE
        );
    }

    public static Account makeWithNewBalance(Account src, BigDecimal newBalance) {
        return new GeneralAccount(
                src.getId(),
                src.getTitle(),
                src.getCurrency(),
                newBalance,
                src.getIsClosed()
        );
    }

    public static Account makeWithSetClosed(Account src) {
        return new GeneralAccount(
                src.getId(),
                src.getTitle(),
                src.getCurrency(),
                src.getBalance(),
                true
        );
    }

}
