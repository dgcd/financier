package dgcd.financier.core.usecase.impl;

import dgcd.financier.core.domain.model.Rate;
import dgcd.financier.core.usecase.api.RatesUpdateUsecase;
import dgcd.financier.core.usecase.api.port.repository.MiscRepository;
import dgcd.financier.core.usecase.api.port.repository.RatesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class RatesUpdateUsecaseImpl implements RatesUpdateUsecase {

    private final MiscRepository miscRepository;
    private final RatesRepository ratesRepository;

    @Override
    public void execute(RequestDto request) {
        if (miscRepository.databaseIsEmpty()) {
            log.warn("Database is not empty, skipping rate saving...");
            return;
        }

        var rate = new Rate()
                .setDate(request.date())
                .setUsd(request.usd())
                .setEur(request.eur())
                .validate();

        ratesRepository.createOrUpdate(rate);
    }

}
