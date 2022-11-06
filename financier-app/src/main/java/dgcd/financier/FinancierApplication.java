package dgcd.financier;

import dgcd.financier.core.domain.Account;
import dgcd.financier.core.domain.Currency;
import dgcd.financier.core.domain.factory.AccountFactory;
import dgcd.financier.core.usecase.InitDataGetUsecase;
import dgcd.financier.core.usecase.impl.InitDataGetUsecaseImpl;
import dgcd.financier.core.usecase.port.repository.OperationsRepository;
import dgcd.financier.core.usecase.port.service.RatesService;
import dgcd.financier.rates.RatesServiceImpl;
import dgcd.financier.repository.impl.OperationRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FinancierApplication {

    private static final Account account = AccountFactory.makeNew("account", Currency.RUB);
    private static final InitDataGetUsecase init = new InitDataGetUsecaseImpl(null, null, null, null, null);
    private static final RatesService rates = new RatesServiceImpl();
    private static final OperationsRepository operationsRepository = new OperationRepositoryImpl(null);

    public static void main(String[] args) {
        SpringApplication.run(FinancierApplication.class, args);
    }

}
