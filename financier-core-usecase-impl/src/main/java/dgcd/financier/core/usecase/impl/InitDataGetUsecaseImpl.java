package dgcd.financier.core.usecase.impl;

import dgcd.financier.core.domain.model.Account;
import dgcd.financier.core.domain.model.Category;
import dgcd.financier.core.domain.model.Operation;
import dgcd.financier.core.domain.model.Rate;
import dgcd.financier.core.usecase.api.InitDataGetUsecase;
import dgcd.financier.core.usecase.api.dto.InitDataGetResponseDto;
import dgcd.financier.core.usecase.api.error.CommonError;
import dgcd.financier.core.usecase.api.port.repository.AccountsRepository;
import dgcd.financier.core.usecase.api.port.repository.CategoriesRepository;
import dgcd.financier.core.usecase.api.port.repository.OperationsRepository;
import dgcd.financier.core.usecase.api.port.repository.RatesRepository;
import dgcd.financier.core.usecase.impl.mapper.AccountMapper;
import dgcd.financier.core.usecase.impl.mapper.CategoryMapper;
import dgcd.financier.core.usecase.impl.mapper.OperationMapper;
import io.vavr.control.Either;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

import static dgcd.financier.core.domain.CurrencyType.EUR;
import static dgcd.financier.core.domain.CurrencyType.USD;
import static dgcd.financier.core.usecase.api.utils.EitherUtils.toRight;
import static io.vavr.control.Either.right;
import static java.math.BigDecimal.ONE;

@RequiredArgsConstructor
public class InitDataGetUsecaseImpl implements InitDataGetUsecase {

    private final AccountsRepository accountsRepository;
    private final CategoriesRepository categoriesRepository;
    private final OperationsRepository operationsRepository;
    private final RatesRepository ratesRepository;


    @Override
    public Either<CommonError, InitDataGetResponseDto> execute() {
        return createContext()
                .flatMap(this::retrieveAccounts)
                .flatMap(this::retrieveCategories)
                .flatMap(this::retrieveOperations)
                .flatMap(this::retrieveRate)
                .map(this::mapResponse);
    }


    private static Either<CommonError, Context> createContext() {
        return right(new Context());
    }


    private Either<CommonError, Context> retrieveAccounts(Context context) {
        return toRight(accountsRepository.findAll())
                .map(context::setAccounts);
    }


    private Either<CommonError, Context> retrieveCategories(Context context) {
        return toRight(categoriesRepository.findAll())
                .map(context::setCategories);
    }


    private Either<CommonError, Context> retrieveOperations(Context context) {
        return toRight(operationsRepository.findAllNotCanceled())
                .map(context::setOperations);
    }


    private Either<CommonError, Context> retrieveRate(Context context) {
        return toRight(ratesRepository.getLastRate())
                .map(opt -> opt.orElseGet(() -> new Rate(null, ONE, ONE)))
                .map(context::setRate);
    }


    private InitDataGetResponseDto mapResponse(Context context) {
        var rate = context.getRate();
        return new InitDataGetResponseDto(
                AccountMapper.INSTANCE.fromDomain(context.getAccounts()),
                CategoryMapper.INSTANCE.fromDomain(context.getCategories()),
                OperationMapper.INSTANCE.fromDomain(context.getOperations()),
                Map.of(USD.name(), rate.getUsd(), EUR.name(), rate.getEur())
        );
    }


    @Getter
    @Setter
    @Accessors(chain = true)
    private static class Context {

        List<Account> accounts;
        List<Category> categories;
        List<Operation> operations;
        Rate rate;

    }

}
