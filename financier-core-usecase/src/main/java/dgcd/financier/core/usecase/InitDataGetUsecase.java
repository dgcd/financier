package dgcd.financier.core.usecase;

import dgcd.financier.core.domain.Account;
import dgcd.financier.core.domain.Category;
import dgcd.financier.core.domain.Operation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@FunctionalInterface
public interface InitDataGetUsecase {

    Response execute();


    @Getter
    @RequiredArgsConstructor
    class Response {

        private final List<Account> accounts;
        private final List<Category> categories;
        private final List<Operation> operations;
        private final Map<String, BigDecimal> rates;
        private final Map<String, String> techInfo;

    }

}
