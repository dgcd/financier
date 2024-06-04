package dgcd.financier.core.usecase.impl;

import dgcd.financier.core.domain.model.Rate;
import dgcd.financier.core.usecase.api.RatesUpdateUsecase;
import dgcd.financier.core.usecase.api.port.repository.RatesRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RatesUpdateUsecaseImpl implements RatesUpdateUsecase {

    private final RatesRepository ratesRepository;

    @Override
    public void execute(RequestDto request) {
        var rate = new Rate()
                .setDate(request.date())
                .setUsd(request.usd())
                .setEur(request.eur())
                .validate();

        ratesRepository.createOrUpdate(rate);
    }

}
