package dgcd.financier.core.domain.implementation;

import dgcd.financier.core.domain.Rates;
import dgcd.financier.core.domain.validation.RatesValidator;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@ToString
public class GeneralRates implements Rates {

    private final LocalDate date;
    private final BigDecimal eurRate;
    private final BigDecimal usdRate;


    public GeneralRates(
            LocalDate date,
            BigDecimal eurRate,
            BigDecimal usdRate
    ) {
        this.date = date;
        this.eurRate = eurRate;
        this.usdRate = usdRate;
        RatesValidator.validate(this);
    }

}
