package dgcd.financier.rates;

import dgcd.financier.core.usecase.port.service.RatesService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static dgcd.financier.core.domain.Currency.EUR;
import static dgcd.financier.core.domain.Currency.USD;
import static org.assertj.core.api.Assertions.assertThat;

class RatesServiceImplTest {

    private final RatesService ratesService = new RatesServiceImpl();

    @Test
    void test_getRates_OK() {
        var rates = ratesService.getRates();

        assertThat(rates).hasSize(2);
        assertThat(rates).extracting(USD.name()).isEqualTo(BigDecimal.valueOf(84));
        assertThat(rates).extracting(EUR.name()).isEqualTo(BigDecimal.valueOf(94));
    }

}
