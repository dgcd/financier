package dgcd.financier.config;

import dgcd.financier.core.usecase.AccountCreateUsecase;
import dgcd.financier.core.usecase.InitDataGetUsecase;
import dgcd.financier.core.usecase.impl.AccountCreateUsecaseImpl;
import dgcd.financier.core.usecase.impl.InitDataGetUsecaseImpl;
import dgcd.financier.core.usecase.port.repository.AccountsRepository;
import dgcd.financier.core.usecase.port.repository.CategoriesRepository;
import dgcd.financier.core.usecase.port.repository.OperationsRepository;
import dgcd.financier.core.usecase.port.service.RatesService;
import dgcd.financier.core.usecase.port.service.TechInfoService;
import dgcd.financier.rates.RatesServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public RatesService ratesService() {
        return new RatesServiceImpl();
    }


    @Bean
    public InitDataGetUsecase initDataGetUsecase(
            AccountsRepository accountsRepository,
            CategoriesRepository categoriesRepository,
            OperationsRepository operationsRepository,
            RatesService ratesService,
            TechInfoService techInfoService
    ) {
        return new InitDataGetUsecaseImpl(
                accountsRepository,
                categoriesRepository,
                operationsRepository,
                ratesService,
                techInfoService
        );
    }

    @Bean
    public AccountCreateUsecase accountCreateUsecase(AccountsRepository accountsRepository) {
        return new AccountCreateUsecaseImpl(accountsRepository);
    }

}
