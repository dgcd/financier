package dgcd.financier.core.domain.model;

import dgcd.financier.core.domain.OperationType;
import dgcd.financier.core.domain.Validatable;
import dgcd.financier.core.domain.validation.OperationValidator;
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
@Accessors(chain = true)
public class Operation implements Validatable<Operation> {

    private Long id;

    private LocalDate date;
    private Long accountId;
    private OperationType type;
    private BigDecimal quantity;
    private BigDecimal amount;
    private Long subcategoryId;
    private String comment;
    private String counterparty;
    private String correlationId;
    private boolean canceled;

    // from db only
    private Account account;
    private Category subcategory;


    @Override
    public Operation validate() {
        return OperationValidator.validate(this);
    }

}
