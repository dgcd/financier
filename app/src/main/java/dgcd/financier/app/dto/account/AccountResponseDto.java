package dgcd.financier.app.dto.account;

import dgcd.financier.app.domain.dict.Currency;
import dgcd.financier.app.domain.model.Account;

import java.math.BigDecimal;

public record AccountResponseDto(
        Long id,
        String title,
        Currency currency,
        BigDecimal balance
) {

    public static AccountResponseDto of(Account account) {
        return new AccountResponseDto(
                account.getId(),
                account.getTitle(),
                account.getCurrency(),
                account.getBalance()
        );
    }

}
