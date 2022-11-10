package dgcd.financier.core.usecase;

import dgcd.financier.core.domain.Account;
import dgcd.financier.core.domain.Currency;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public interface AccountCreateUsecase extends Usecase<AccountCreateUsecase.Request, AccountCreateUsecase.Response> {

    @Override
    AccountCreateUsecase.Response execute(AccountCreateUsecase.Request request);


    @Getter
    @RequiredArgsConstructor
    class Request implements Usecase.Request {

        private final String title;
        private final Currency currency;

    }

    @Getter
    @RequiredArgsConstructor
    class Response implements Usecase.Response {

        private final Account account;

    }

}
