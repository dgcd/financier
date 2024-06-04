package dgcd.financier.core.usecase.api;

import java.math.BigDecimal;
import java.time.LocalDate;

@FunctionalInterface
public interface RatesUpdateUsecase {

    void execute(RequestDto request);


    record RequestDto(
            LocalDate date,
            BigDecimal usd,
            BigDecimal eur
    ) {}

}
