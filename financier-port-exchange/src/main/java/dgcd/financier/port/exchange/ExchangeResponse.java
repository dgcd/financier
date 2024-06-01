package dgcd.financier.port.exchange;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Map;

@Getter
@Setter
@ToString
public class ExchangeResponse {

    private boolean success;
    private Long timestamp;
    private String base;
    private LocalDate date;
    private Map<String, Double> rates;

}
