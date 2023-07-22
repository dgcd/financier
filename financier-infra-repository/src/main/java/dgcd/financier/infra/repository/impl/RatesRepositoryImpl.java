package dgcd.financier.infra.repository.impl;

import dgcd.financier.core.usecase.port.repository.RatesRepository;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.Map;

import static dgcd.financier.core.domain.Currency.EUR;
import static dgcd.financier.core.domain.Currency.USD;

@Slf4j
public class RatesRepositoryImpl implements RatesRepository {

    public static final BigDecimal RATE_USD = BigDecimal.valueOf(90);
    public static final BigDecimal RATE_EUR = BigDecimal.valueOf(98);


    @Override
    public Map<String, BigDecimal> getRates() {
        var rates = Map.of(
                USD.name(), RATE_USD,
                EUR.name(), RATE_EUR
        );

        log.debug("[getRates] rates: {}", rates);

        return rates;
    }

}
