package dgcd.financier.core.domain;

import java.math.BigDecimal;

public interface Account {

    Long getIdentity();

    String getTitle();

    Currency getCurrency();

    BigDecimal getBalance();

    Boolean getIsClosed();

}
