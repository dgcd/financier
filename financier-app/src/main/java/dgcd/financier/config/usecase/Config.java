package dgcd.financier.config.usecase;

import dgcd.financier.core.usecase.AccountCloseUsecase;
import dgcd.financier.core.usecase.AccountCreateUsecase;
import dgcd.financier.core.usecase.AlldataExportUsecase;
import dgcd.financier.core.usecase.AlldataImportUsecase;
import dgcd.financier.core.usecase.CategoryCreateUsecase;
import dgcd.financier.core.usecase.InitDataGetUsecase;
import dgcd.financier.core.usecase.OperationCancelUsecase;
import dgcd.financier.core.usecase.OperationCreateUsecase;
import dgcd.financier.core.usecase.impl.AccountCloseUsecaseImpl;
import dgcd.financier.core.usecase.impl.AccountCreateUsecaseImpl;
import dgcd.financier.core.usecase.impl.AlldataExportUsecaseImpl;
import dgcd.financier.core.usecase.impl.AlldataImportUsecaseImpl;
import dgcd.financier.core.usecase.impl.CategoryCreateUsecaseImpl;
import dgcd.financier.core.usecase.impl.InitDataGetUsecaseImpl;
import dgcd.financier.core.usecase.impl.OperationCancelUsecaseImpl;
import dgcd.financier.core.usecase.impl.OperationCreateUsecaseImpl;
import dgcd.financier.core.usecase.port.repository.AccountsRepository;
import dgcd.financier.core.usecase.port.repository.CategoriesRepository;
import dgcd.financier.core.usecase.port.repository.OperationsRepository;
import dgcd.financier.core.usecase.port.repository.RatesRepository;
import dgcd.financier.core.usecase.port.service.TechInfoService;
import dgcd.financier.infra.repository.impl.RatesRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

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


    @Bean
    public OperationCreateUsecase operationCreateUsecase(
            AccountsRepository accountsRepository,
            CategoriesRepository categoriesRepository,
            OperationsRepository operationsRepository
    ) {
        return new OperationCreateUsecaseImpl(
                accountsRepository,
                categoriesRepository,
                operationsRepository
        );
    }


    @Bean
    public OperationCancelUsecase operationCancelUsecase(
            AccountsRepository accountsRepository,
            OperationsRepository operationsRepository
    ) {
        return new OperationCancelUsecaseImpl(
                accountsRepository,
                operationsRepository
        );
    }


    @Bean
    public AlldataExportUsecase alldataExportUsecase(
            AccountsRepository accountsRepository,
            CategoriesRepository categoriesRepository,
            OperationsRepository operationsRepository
    ) {
        return new AlldataExportUsecaseImpl(
                accountsRepository,
                categoriesRepository,
                operationsRepository
        );
    }


    @Bean
    public AlldataImportUsecase alldataImportUsecase(
            AccountsRepository accountsRepository,
            CategoriesRepository categoriesRepository,
            OperationsRepository operationsRepository
    ) {
        return new AlldataImportUsecaseImpl(
                accountsRepository,
                categoriesRepository,
                operationsRepository
        );
    }

}