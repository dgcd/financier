package dgcd.financier.core.domain;

import java.math.BigDecimal;

public interface Account {

    Long getId();

    String getTitle();

    Currency getCurrency();

    BigDecimal getBalance();

    Boolean getIsClosed();

}
