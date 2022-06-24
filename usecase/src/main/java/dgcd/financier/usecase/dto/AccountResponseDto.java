package dgcd.financier.usecase.dto;

import dgcd.financier.domain.dictionary.Currency;

import java.math.BigDecimal;

public record AccountResponseDto(
        Long id,
        String title,
        Currency currency,
        BigDecimal balance,
        Boolean isClosed
) {

//    public static AccountResponseDto of(Account account) {
//        return new AccountResponseDto(
//                account.getId(),
//                account.getTitle(),
//                account.getCurrency(),
//                account.getBalance(),
//                account.getIsClosed()
//        );
//    }

}
