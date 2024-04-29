package dgcd.financier.core.usecase;

import dgcd.financier.core.domain.Account;
import dgcd.financier.core.domain.Currency;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@FunctionalInterface
public interface AccountCreateUsecase {

    Response execute(Request request);


    @Getter
    @RequiredArgsConstructor
    class Request {

        private final String title;
        private final Currency currency;

    }

    @Getter
    @RequiredArgsConstructor
    class Response {

        private final Account account;

    }

}
