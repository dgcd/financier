package dgcd.financier.core.usecase.impl;

import dgcd.financier.core.domain.Rates;
import dgcd.financier.core.usecase.InitDataGetUsecase;
import dgcd.financier.core.usecase.port.repository.AccountsRepository;
import dgcd.financier.core.usecase.port.repository.CategoriesRepository;
import dgcd.financier.core.usecase.port.repository.OperationsRepository;
import dgcd.financier.core.usecase.port.repository.RatesRepository;
import dgcd.financier.core.usecase.port.service.TechInfoService;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

import static dgcd.financier.core.domain.Currency.EUR;
import static dgcd.financier.core.domain.Currency.USD;

@RequiredArgsConstructor
public class InitDataGetUsecaseImpl implements InitDataGetUsecase {

    private final AccountsRepository accountsRepository;
    private final CategoriesRepository categoriesRepository;
    private final OperationsRepository operationsRepository;

    private final RatesRepository ratesRepository;
    private final TechInfoService techInfoService;


    @Override
    public Response execute(Request ignored) {
        return new Response(
                accountsRepository.findAll(),
                categoriesRepository.findAll(),
                operationsRepository.findAllNotCanceled(),
                mapRates(ratesRepository.getRates()),
                techInfoService.getTechInfo()
        );
    }

    private Map<String, BigDecimal> mapRates(Rates rates) {
        return Map.of(
                EUR.name(), rates.getEurRate(),
                USD.name(), rates.getUsdRate()
        );
    }

}
