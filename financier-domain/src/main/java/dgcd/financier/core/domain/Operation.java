package dgcd.financier.core.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface Operation {

    Long getId();

    LocalDate getDate();

    Account getAccount();

    OperationType getType();

    BigDecimal getAmount();

    BigDecimal getQuantity();

    Category getSubcategory();

    String getComment();

    String getCounterparty();

    Boolean getIsCanceled();

    String getCorrelationId();

}
