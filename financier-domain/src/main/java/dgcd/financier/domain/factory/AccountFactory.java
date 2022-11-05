package dgcd.financier.domain.factory;

import dgcd.financier.domain.Account;
import dgcd.financier.domain.Currency;
import dgcd.financier.domain.implementation.GeneralAccount;

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
