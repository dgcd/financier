package dgcd.financier.core.usecase;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface RatesUpdateUsecase {

    void execute(RatesUpdateUsecase.Request request);


    record Request(
            LocalDate date,
            BigDecimal eur,
            BigDecimal usd
    ) {}

}
