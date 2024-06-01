package dgcd.financier.core.domain.model;

import dgcd.financier.core.domain.Validatable;
import dgcd.financier.core.domain.validation.RateValidator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Rate implements Validatable {

    private LocalDate date;
    private BigDecimal usd;
    private BigDecimal eur;


    @Override
    public void validate() {
        RateValidator.validate(this);
    }

}
