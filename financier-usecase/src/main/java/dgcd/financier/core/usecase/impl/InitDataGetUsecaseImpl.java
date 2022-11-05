package dgcd.financier.core.usecase.impl;

import dgcd.financier.core.usecase.InitDataGetUsecase;
import dgcd.financier.core.usecase.port.repository.AccountsRepository;
import dgcd.financier.core.usecase.port.repository.CategoriesRepository;
import dgcd.financier.core.usecase.port.repository.OperationsRepository;
import dgcd.financier.core.usecase.port.service.RatesService;
import dgcd.financier.core.usecase.port.service.TechInfoService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class InitDataGetUsecaseImpl implements InitDataGetUsecase {

    private final AccountsRepository accountsRepository;
    private final CategoriesRepository categoriesRepository;
    private final OperationsRepository operationsRepository;

    private final RatesService ratesService;
    private final TechInfoService techInfoService;


    @Override
    public InitDataGetUsecase.Response execute(InitDataGetUsecase.Request request) {
        return new InitDataGetUsecase.Response(
                accountsRepository.findAll(),
                categoriesRepository.findAll(),
                operationsRepository.findAllNotCanceled(),
                ratesService.getRates(),
                techInfoService.getTechInfo()
        );
    }

}
