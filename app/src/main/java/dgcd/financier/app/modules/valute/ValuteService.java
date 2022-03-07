package dgcd.financier.app.modules.valute;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ValuteService {

    @Getter
    private final BigDecimal rateUsd = BigDecimal.valueOf(105);

    @Getter
    private final BigDecimal rateEur = BigDecimal.valueOf(119);

}
