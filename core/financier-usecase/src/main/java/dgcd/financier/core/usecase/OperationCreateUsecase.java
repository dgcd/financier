package dgcd.financier.core.usecase;

import dgcd.financier.core.domain.Account;
import dgcd.financier.core.domain.Operation;
import dgcd.financier.core.domain.OperationType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface OperationCreateUsecase extends Usecase<OperationCreateUsecase.Request, OperationCreateUsecase.Response> {

    @Override
    OperationCreateUsecase.Response execute(OperationCreateUsecase.Request request);


    @Getter
    @RequiredArgsConstructor
    class Request implements Usecase.Request {

        private final LocalDate date;
        private final Long accountIdentity;
        private final Long accountToIdentity;

        private final OperationType operationType;

        private final BigDecimal amount;
        private final BigDecimal amountTo;
        private final BigDecimal quantity;

        private final Long subcategoryIdentity;

        private final String comment;
        private final String counterparty;

    }

    @Getter
    @RequiredArgsConstructor
    class Response implements Usecase.Response {

        private final List<Operation> newOperations;
        private final List<Account> updatedAccounts;

    }

}
