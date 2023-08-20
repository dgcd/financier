package dgcd.financier.core.usecase.impl;

import dgcd.financier.core.domain.factory.RatesFactory;
import dgcd.financier.core.usecase.RatesUpdateUsecase;
import dgcd.financier.core.usecase.port.repository.RatesRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RatesUpdateUsecaseImpl implements RatesUpdateUsecase {

    private final RatesRepository ratesRepository;

    @Override
    public void execute(Request request) {
        // todo validation

        var rates = RatesFactory.make(request.date(), request.eur(), request.usd());

        ratesRepository.updateRates(rates);
    }

}
