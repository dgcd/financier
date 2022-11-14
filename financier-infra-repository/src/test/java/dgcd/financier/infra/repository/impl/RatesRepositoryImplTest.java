package dgcd.financier.infra.repository.impl;

import dgcd.financier.core.usecase.port.repository.RatesRepository;
import org.junit.jupiter.api.Test;

import static dgcd.financier.core.domain.Currency.EUR;
import static dgcd.financier.core.domain.Currency.USD;
import static dgcd.financier.infra.repository.impl.RatesRepositoryImpl.RATE_EUR;
import static dgcd.financier.infra.repository.impl.RatesRepositoryImpl.RATE_USD;
import static org.assertj.core.api.Assertions.assertThat;

class RatesRepositoryImplTest {

    private final RatesRepository ratesRepository = new RatesRepositoryImpl();

    @Test
    void test_getRates_OK() {
        var rates = ratesRepository.getRates();

        assertThat(rates).hasSize(2);
        assertThat(rates).extracting(USD.name()).isEqualTo(RATE_USD);
        assertThat(rates).extracting(EUR.name()).isEqualTo(RATE_EUR);
    }

}
