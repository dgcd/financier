package dgcd.financier.infra.repository.impl;

import dgcd.financier.core.domain.Rates;
import dgcd.financier.core.domain.factory.RatesFactory;
import dgcd.financier.core.usecase.port.repository.RatesRepository;
import dgcd.financier.infra.repository.entity.RatesEntity;
import dgcd.financier.infra.repository.jpa.RatesJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

import static java.math.BigDecimal.ZERO;

@Slf4j
@Repository
@RequiredArgsConstructor
public class RatesRepositoryImpl implements RatesRepository {

    private final RatesJpaRepository ratesJpaRepository;

    @Override
    public Rates getRates() {
        var rates = ratesJpaRepository.findFirstByOrderByDateDesc()
                .map(r -> RatesFactory.make(r.getDate(), r.getEur(), r.getUsd()))
                .orElse(RatesFactory.make(LocalDate.now(), ZERO, ZERO));

        log.debug("[getRates] rates: {}", rates);

        return rates;
    }


    @Override
    public void updateRates(Rates rates) {
        var ratesEntity = new RatesEntity(rates.getDate(), rates.getEurRate(), rates.getUsdRate());
        var savedEntity = ratesJpaRepository.save(ratesEntity);
        log.debug("[updateRates] rates: {}", savedEntity);
    }

}
