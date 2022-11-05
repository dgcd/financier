package dgcd.financier.core.usecase.port.service;

import java.math.BigDecimal;
import java.util.Map;

public interface RatesService {

    Map<String, BigDecimal> getRates();

}
