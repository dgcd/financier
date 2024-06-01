package dgcd.financier.core.domain.model;

import dgcd.financier.core.domain.OperationType;
import dgcd.financier.core.domain.Validatable;
import dgcd.financier.core.domain.validation.OperationValidator;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode
@Accessors(chain = true)
public class Operation implements Validatable {

    private Long id;
    private LocalDate date;
    private Long accountId;
    private OperationType type;
    private BigDecimal amount;
    private BigDecimal quantity;
    private Long subcategoryId;
    private String comment;
    private String counterparty;
    private String correlationId;
    private boolean canceled;

    private Account account;
    private Category subcategory;


    @Override
    public void validate() {
        OperationValidator.validate(this);
    }

}
