package dgcd.financier.core.domain.model;

import dgcd.financier.core.domain.CurrencyType;
import dgcd.financier.core.domain.Validatable;
import dgcd.financier.core.domain.validation.AccountValidator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@Accessors(chain = true)
public final class Account implements Validatable<Account> {

    private Long id;
    private String title;
    private CurrencyType currency;
    private BigDecimal balance = ZERO;
    private boolean closed = false;

    @Override
    public Account validate() {
        return AccountValidator.validate(this);
    }

}
