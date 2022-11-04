package dgcd.financier.app.modules.account.dto;

import dgcd.financier.app.modules.account.Account;
import dgcd.financier.domain.Currency;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import static java.math.BigDecimal.ZERO;

public record AccountCreateRequestDto(
        @NotNull(message = "Title can not be null")
        @Size(min = 1, max = 40, message = "Account title length must be 1..40")
        String title,

        @NotNull(message = "Currency can not be null")
        Currency currency
) {

    public Account makeAccount() {
        return new Account(
                null,
                this.title(),
                this.currency(),
                ZERO,
                false
        );
    }

}
