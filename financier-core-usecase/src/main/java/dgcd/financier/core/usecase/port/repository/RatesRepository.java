package dgcd.financier.core.usecase.port.repository;

import java.math.BigDecimal;
import java.util.Map;

public interface RatesRepository {

    Map<String, BigDecimal> getRates();

}
