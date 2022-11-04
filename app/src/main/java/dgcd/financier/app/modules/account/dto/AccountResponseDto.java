package dgcd.financier.app.modules.account.dto;

import dgcd.financier.app.modules.account.Account;
import dgcd.financier.domain.Currency;

import java.math.BigDecimal;

public record AccountResponseDto(
        Long id,
        String title,
        Currency currency,
        BigDecimal balance,
        Boolean isClosed
) {

    public static AccountResponseDto of(Account account) {
        return new AccountResponseDto(
                account.getId(),
                account.getTitle(),
                account.getCurrency(),
                account.getBalance(),
                account.getIsClosed()
        );
    }

}
