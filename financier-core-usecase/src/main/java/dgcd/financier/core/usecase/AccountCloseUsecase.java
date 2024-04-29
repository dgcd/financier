package dgcd.financier.core.usecase;

import dgcd.financier.core.domain.Account;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@FunctionalInterface
public interface AccountCloseUsecase {

    Response execute(Request request);


    @Getter
    @RequiredArgsConstructor
    class Request {

        private final Long identity;

    }

    @Getter
    @RequiredArgsConstructor
    class Response {

        private final Account account;

    }

}
