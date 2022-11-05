package dgcd.financier.rates;

import dgcd.financier.core.usecase.port.service.RatesService;

import java.math.BigDecimal;
import java.util.Map;

import static dgcd.financier.core.domain.Currency.EUR;
import static dgcd.financier.core.domain.Currency.USD;

public class RatesServiceImpl implements RatesService {

    private final BigDecimal RATE_USD = BigDecimal.valueOf(84);
    private final BigDecimal RATE_EUR = BigDecimal.valueOf(94);


    @Override
    public Map<String, BigDecimal> getRates() {
        return Map.of(
                USD.name(), RATE_USD,
                EUR.name(), RATE_EUR
        );

    }

}
