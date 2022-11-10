package dgcd.financier.core.usecase;

import dgcd.financier.core.domain.Account;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public interface AccountCloseUsecase extends Usecase<AccountCloseUsecase.Request, AccountCloseUsecase.Response> {

    @Override
    AccountCloseUsecase.Response execute(AccountCloseUsecase.Request request);


    @Getter
    @RequiredArgsConstructor
    class Request implements Usecase.Request {

        private final Long identity;

    }

    @Getter
    @RequiredArgsConstructor
    class Response implements Usecase.Response {

        private final Account account;

    }

}
