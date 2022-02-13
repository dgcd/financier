package dgcd.financier.app.modules.account.dto;

import dgcd.financier.app.dictionary.Currency;
import dgcd.financier.app.modules.account.Account;

import static java.math.BigDecimal.ZERO;

public record AccountCreateRequestDto(
        String title,
        Currency currency
) {

    public Account makeAccount() {
        return new Account(
                null,
                this.title(),
                this.currency(),
                ZERO
        );
    }

}
