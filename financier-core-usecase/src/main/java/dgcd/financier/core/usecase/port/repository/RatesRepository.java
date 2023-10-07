package dgcd.financier.core.usecase.port.repository;

import dgcd.financier.core.domain.Rates;

public interface RatesRepository {

    Rates getRates();

    void updateRates(Rates rates);

}
