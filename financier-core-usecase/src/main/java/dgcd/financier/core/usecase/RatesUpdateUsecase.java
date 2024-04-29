package dgcd.financier.core.usecase;

import java.math.BigDecimal;
import java.time.LocalDate;

@FunctionalInterface
public interface RatesUpdateUsecase {

    void execute(Request request);


    record Request(
            LocalDate date,
            BigDecimal eur,
            BigDecimal usd
    ) {}

}
