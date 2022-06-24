package dgcd.financier.usecase.dto;

import dgcd.financier.domain.dictionary.Currency;

public record AccountCreateRequestDto(
//        @NotNull(message = "Title can not be null")
//        @Size(min = 1, max = 30, message = "Account title length must be 1..30")
        String title,

//        @NotNull(message = "Currency can not be null")
        Currency currency
) {

//    public Account makeAccount() {
//        return new Account(
//                null,
//                this.title(),
//                this.currency(),
//                ZERO,
//                false
//        );
//    }

}
