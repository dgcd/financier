package dgcd.financier.core.usecase;

import dgcd.financier.core.domain.Account;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@FunctionalInterface
public interface OperationCancelUsecase {

    Response execute(Request request);


    @Getter
    @RequiredArgsConstructor
    class Request {

        private final Long operationIdentity;

    }

    @Getter
    @RequiredArgsConstructor
    class Response {

        private final List<Long> canceledOperationsIdentities;
        private final List<Account> updatedAccounts;

    }

}
