package dgcd.financier.core.domain.factory;

import dgcd.financier.core.domain.Rates;
import dgcd.financier.core.domain.implementation.GeneralRates;

import java.math.BigDecimal;
import java.time.LocalDate;

public class RatesFactory {

    public static Rates make(
            LocalDate date,
            BigDecimal eurRate,
            BigDecimal usdRate
    ) {
        return new GeneralRates(
                date,
                eurRate,
                usdRate
        );
    }

}
