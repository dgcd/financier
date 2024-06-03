package dgcd.financier.config;

import dgcd.financier.core.usecase.api.AccountCloseUsecase;
import dgcd.financier.core.usecase.api.AccountCreateUsecase;
import dgcd.financier.core.usecase.api.CategoryCreateUsecase;
import dgcd.financier.core.usecase.api.InitDataGetUsecase;
import dgcd.financier.core.usecase.api.OperationCreateUsecase;
import dgcd.financier.core.usecase.api.port.repository.AccountsRepository;
import dgcd.financier.core.usecase.api.port.repository.CategoriesRepository;
import dgcd.financier.core.usecase.api.port.repository.OperationsRepository;
import dgcd.financier.core.usecase.api.port.repository.RatesRepository;
import dgcd.financier.core.usecase.impl.AccountCloseUsecaseImpl;
import dgcd.financier.core.usecase.impl.AccountCreateUsecaseImpl;
import dgcd.financier.core.usecase.impl.CategoryCreateUsecaseImpl;
import dgcd.financier.core.usecase.impl.InitDataGetUsecaseImpl;
import dgcd.financier.core.usecase.impl.OperationCreateUsecaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UsecaseConfig {

    @Bean
    public InitDataGetUsecase initDataGetUsecase(
            AccountsRepository accountsRepository,
            CategoriesRepository categoriesRepository,
            OperationsRepository operationsRepository,
            RatesRepository ratesRepository
    ) {
        return new InitDataGetUsecaseImpl(
                accountsRepository,
                categoriesRepository,
                operationsRepository,
                ratesRepository
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

//    @Bean
//    public OperationCancelUsecase operationCancelUsecase(
//            AccountsRepository accountsRepository,
//            OperationsRepository operationsRepository
//    ) {
//        return new OperationCancelUsecaseImpl(
//                accountsRepository,
//                operationsRepository
//        );
//    }
//
//
//    @Bean
//    public AlldataExportUsecase alldataExportUsecase(
//            AccountsRepository accountsRepository,
//            CategoriesRepository categoriesRepository,
//            OperationsRepository operationsRepository,
//            RatesRepository ratesRepository
//    ) {
//        return new AlldataExportUsecaseImpl(
//                accountsRepository,
//                categoriesRepository,
//                operationsRepository,
//                ratesRepository
//        );
//    }
//
//
//    @Bean
//    public AlldataImportUsecase alldataImportUsecase(
//            AccountsRepository accountsRepository,
//            CategoriesRepository categoriesRepository,
//            OperationsRepository operationsRepository,
//            RatesRepository ratesRepository
//    ) {
//        return new AlldataImportUsecaseImpl(
//                accountsRepository,
//                categoriesRepository,
//                operationsRepository,
//                ratesRepository
//        );
//    }
//
//
//    @Bean
//    public RatesUpdateUsecase ratesUpdateUsecase(
//            RatesRepository ratesRepository
//    ) {
//        return new RatesUpdateUsecaseImpl(
//                ratesRepository
//        );
//    }

}
