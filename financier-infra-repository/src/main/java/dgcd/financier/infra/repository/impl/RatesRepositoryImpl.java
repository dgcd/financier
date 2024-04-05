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
import java.util.List;

import static java.math.BigDecimal.ZERO;

@Slf4j
@Repository
@RequiredArgsConstructor
public class RatesRepositoryImpl implements RatesRepository {

    private final RatesJpaRepository ratesJpaRepository;

    @Override
    public List<Rates> findAll() {
        var rates = ratesJpaRepository.findAll()
                .stream()
                .map(r -> RatesFactory.make(r.getDate(), r.getEur(), r.getUsd()))
                .toList();

        log.debug("[findAll] rates size: {}", rates.size());

        return rates;
    }

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


    @Override
    public List<Rates> saveAll(List<Rates> rates) {
        var ratesEntities = rates.stream()
                .map(r -> new RatesEntity(r.getDate(), r.getEurRate(), r.getUsdRate()))
                .toList();
        var savedRates = ratesJpaRepository.saveAll(ratesEntities)
                .stream()
                .map(re -> RatesFactory.make(re.getDate(), re.getEur(), re.getUsd()))
                .toList();

        if (log.isDebugEnabled()) {
            savedRates.forEach(r -> log.debug("[saveAll] rates: {}", r));
        }

        return savedRates;
    }

}
