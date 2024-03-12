package dgcd.financier.core.usecase.port.repository;

import dgcd.financier.core.domain.Rates;

import java.util.List;

public interface RatesRepository {

    List<Rates> findAll();

    Rates getRates();

    void updateRates(Rates rates);

    List<Rates> saveAll(List<Rates> rates);

}
