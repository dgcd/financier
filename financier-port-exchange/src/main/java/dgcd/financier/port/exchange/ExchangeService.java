package dgcd.financier.port.exchange;

import dgcd.financier.core.usecase.impl.RatesUpdateUsecase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

import static java.math.RoundingMode.HALF_UP;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExchangeService {

    @Value("${financier.exchange.enabled:false}")
    private boolean exchangeEnabled;

    @Value("${financier.exchange.api-key}")
    private String exchangeApiKey;

    @Value("${financier.exchange.url}")
    private String exchangeUrl;


    private final RestTemplate restTemplate;
    private final RatesUpdateUsecase ratesUpdateUsecase;


    public void updateRates() {
        if (!exchangeEnabled) {
            log.info("updateRates disabled");
            return;
        }

        log.debug("updateRates started");
        try {
            var url = String.format(exchangeUrl, exchangeApiKey);
            var response = restTemplate.getForObject(url, ExchangeResponse.class);
            log.debug("updateRates response: {}", response);
            if (response == null || !response.isSuccess()) {
                log.warn("updateRates failed");
                return;
            }

            var rub = response.getRates().get("RUB");
            var eur = BigDecimal.valueOf(rub).setScale(RATE_SCALE, HALF_UP);
            var usd = BigDecimal.valueOf(rub / response.getRates().get("USD")).setScale(2, HALF_UP);
            log.info("updateRates RUB/EUR: {}, RUB/USD: {}, date: {}", eur, usd, response.getDate());

            var request = new RatesUpdateUsecase.Request(response.getDate(), eur, usd);

            ratesUpdateUsecase.execute(request);

        } catch (Exception e) {
            log.warn("updateRates exception", e);
        }
    }

}
