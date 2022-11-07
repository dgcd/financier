package dgcd.financier.config;

import dgcd.financier.core.usecase.AccountCloseUsecase;
import dgcd.financier.core.usecase.AccountCreateUsecase;
import dgcd.financier.core.usecase.CategoryCreateUsecase;
import dgcd.financier.core.usecase.InitDataGetUsecase;
import dgcd.financier.core.usecase.impl.AccountCloseUsecaseImpl;
import dgcd.financier.core.usecase.impl.AccountCreateUsecaseImpl;
import dgcd.financier.core.usecase.impl.CategoryCreateUsecaseImpl;
import dgcd.financier.core.usecase.impl.InitDataGetUsecaseImpl;
import dgcd.financier.core.usecase.port.repository.AccountsRepository;
import dgcd.financier.core.usecase.port.repository.CategoriesRepository;
import dgcd.financier.core.usecase.port.repository.OperationsRepository;
import dgcd.financier.core.usecase.port.repository.RatesRepository;
import dgcd.financier.core.usecase.port.service.TechInfoService;
import dgcd.financier.repository.impl.RatesRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UsecaseConfig {

    @Bean
    public RatesRepository ratesService() {
        return new RatesRepositoryImpl();
    }


    @Bean
    public InitDataGetUsecase initDataGetUsecase(
            AccountsRepository accountsRepository,
            CategoriesRepository categoriesRepository,
            OperationsRepository operationsRepository,
            RatesRepository ratesRepository,
            TechInfoService techInfoService
    ) {
        return new InitDataGetUsecaseImpl(
                accountsRepository,
                categoriesRepository,
                operationsRepository,
                ratesRepository,
                techInfoService
        );
    }

    @Bean
    public AccountCreateUsecase accountCreateUsecase(AccountsRepository accountsRepository) {
        return new AccountCreateUsecaseImpl(accountsRepository);
    }

    @Bean
    public AccountCloseUsecase accountCloseUsecase(AccountsRepository accountsRepository) {
        return new AccountCloseUsecaseImpl(accountsRepository);
    }

    @Bean
    public CategoryCreateUsecase categoryCreateUsecase(CategoriesRepository categoriesRepository) {
        return new CategoryCreateUsecaseImpl(categoriesRepository);
    }

}
