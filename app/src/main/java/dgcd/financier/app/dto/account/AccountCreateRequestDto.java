package dgcd.financier.app.dto.account;

import dgcd.financier.app.domain.dict.Currency;
import dgcd.financier.app.domain.model.Account;

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
