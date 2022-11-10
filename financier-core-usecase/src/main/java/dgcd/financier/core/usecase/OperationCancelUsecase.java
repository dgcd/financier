package dgcd.financier.core.usecase;

import dgcd.financier.core.domain.Account;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

public interface OperationCancelUsecase extends Usecase<OperationCancelUsecase.Request, OperationCancelUsecase.Response> {

    @Override
    OperationCancelUsecase.Response execute(OperationCancelUsecase.Request request);


    @Getter
    @RequiredArgsConstructor
    class Request implements Usecase.Request {

        private final Long operationIdentity;

    }

    @Getter
    @RequiredArgsConstructor
    class Response implements Usecase.Response {

        private final List<Long> canceledOperationsIdentities;
        private final List<Account> updatedAccounts;

    }

}
