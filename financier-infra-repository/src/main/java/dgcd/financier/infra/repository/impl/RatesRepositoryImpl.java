package dgcd.financier.infra.repository.impl;

import dgcd.financier.core.usecase.port.repository.RatesRepository;

import java.math.BigDecimal;
import java.util.Map;

import static dgcd.financier.core.domain.Currency.EUR;
import static dgcd.financier.core.domain.Currency.USD;

// todo: stub, implement
public class RatesRepositoryImpl implements RatesRepository {

    public static final BigDecimal RATE_USD = BigDecimal.valueOf(81);
    public static final BigDecimal RATE_EUR = BigDecimal.valueOf(87);


    @Override
    public Map<String, BigDecimal> getRates() {
        return Map.of(
                USD.name(), RATE_USD,
                EUR.name(), RATE_EUR
        );
    }

}
