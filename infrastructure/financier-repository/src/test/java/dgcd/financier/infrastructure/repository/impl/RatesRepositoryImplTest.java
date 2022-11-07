package dgcd.financier.infrastructure.repository.impl;

import dgcd.financier.core.usecase.port.repository.RatesRepository;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static dgcd.financier.core.domain.Currency.EUR;
import static dgcd.financier.core.domain.Currency.USD;
import static org.assertj.core.api.Assertions.assertThat;

class RatesRepositoryImplTest {

    private final RatesRepository ratesRepository = new RatesRepositoryImpl();

    @Test
    void test_getRates_OK() {
        var rates = ratesRepository.getRates();

        assertThat(rates).hasSize(2);
        assertThat(rates).extracting(USD.name()).isEqualTo(BigDecimal.valueOf(62));
        assertThat(rates).extracting(EUR.name()).isEqualTo(BigDecimal.valueOf(61));
    }

}
