package dgcd.financier.core.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface Rates {

    LocalDate getDate();

    BigDecimal getEurRate();

    BigDecimal getUsdRate();

}
