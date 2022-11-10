package dgcd.financier.core.usecase;

import dgcd.financier.core.domain.Account;
import dgcd.financier.core.domain.Category;
import dgcd.financier.core.domain.Operation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface InitDataGetUsecase extends Usecase<InitDataGetUsecase.Request, InitDataGetUsecase.Response> {

    @Override
    InitDataGetUsecase.Response execute(InitDataGetUsecase.Request request);


    class Request implements Usecase.Request {
    }

    @Getter
    @RequiredArgsConstructor
    class Response implements Usecase.Response {

        private final List<Account> accounts;
        private final List<Category> categories;
        private final List<Operation> operations;
        private final Map<String, BigDecimal> rates;
        private final Map<String, String> techInfo;

    }

}
