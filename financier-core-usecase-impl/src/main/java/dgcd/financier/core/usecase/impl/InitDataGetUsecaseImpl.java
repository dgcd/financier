package dgcd.financier.core.usecase.impl;

import dgcd.financier.core.api.InitDataGetUsecase;
import dgcd.financier.core.api.dto.InitDataGetResponseDto;
import dgcd.financier.core.api.error.CommonError;
import dgcd.financier.core.api.port.repository.OperationsRepository;
import dgcd.financier.core.api.port.repository.RatesRepository;
import dgcd.financier.core.domain.model.Account;
import dgcd.financier.core.domain.model.Category;
import dgcd.financier.core.domain.model.Operation;
import dgcd.financier.core.domain.model.Rate;
import dgcd.financier.core.usecase.impl.mapper.AccountMapper;
import dgcd.financier.core.usecase.impl.mapper.CategoryMapper;
import dgcd.financier.core.usecase.impl.mapper.OperationMapper;
import io.vavr.control.Either;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import static dgcd.financier.core.api.utils.EitherUtils.toRight;
import static dgcd.financier.core.domain.CurrencyType.EUR;
import static dgcd.financier.core.domain.CurrencyType.USD;
import static io.vavr.control.Either.right;
import static java.math.BigDecimal.ONE;
import static java.util.stream.Collectors.toSet;

@RequiredArgsConstructor
public class InitDataGetUsecaseImpl implements InitDataGetUsecase {

    private final OperationsRepository operationsRepository;
    private final RatesRepository ratesRepository;


    @Override
    public Either<CommonError, InitDataGetResponseDto> execute() {
        return createContext()
                .flatMap(this::retrieveOperations)
                .flatMap(this::collectCategories)
                .flatMap(this::collectAccounts)
                .flatMap(this::retrieveRate)
                .map(this::mapResponse);
    }


    private static Either<CommonError, Context> createContext() {
        return right(new Context());
    }


    private Either<CommonError, Context> retrieveOperations(Context context) {
        return toRight(operationsRepository.findAllNotCanceled())
                .map(context::setOperations);
    }


    private Either<CommonError, Context> collectCategories(Context context) {
        var categories = new HashSet<Category>();
        context.getOperations().forEach(o -> {
            categories.add(o.getSubcategory());
            if (!o.getSubcategory().isParent()) {
                categories.add(o.getSubcategory().getParent());
            }
        });
        context.setCategories(new ArrayList<>(categories));
        return toRight(context);
    }


    private Either<CommonError, Context> collectAccounts(Context context) {
        var accounts = context.getOperations().stream()
                .map(Operation::getAccount)
                .collect(toSet());
        context.setAccounts(new ArrayList<>(accounts));
        return toRight(context);
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
