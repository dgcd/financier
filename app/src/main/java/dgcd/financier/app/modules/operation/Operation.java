package dgcd.financier.app.modules.operation;

import dgcd.financier.app.dict.OperationType;
import dgcd.financier.app.modules.account.Account;
import dgcd.financier.app.modules.category.Category;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "operations")
public class Operation {

    @Id
    @GeneratedValue(generator = "operations_id_generator")
    @GenericGenerator(
            name = "operations_id_generator",
            strategy = "enhanced-sequence",
            parameters = {
                    @Parameter(name = "sequence_name", value = "operations_id_seq"),
                    @Parameter(name = "increment_size", value = "1"),
                    @Parameter(name = "optimizer", value = "pooled-lo"),
            }
    )
    private Long id;

    @Column(name = "op_date", updatable = false, nullable = false)
    private LocalDate date;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @NotNull
    @Column(name = "op_type", nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private OperationType type;

    @NotNull
    private BigDecimal amount;

    @NotNull
    private BigDecimal quantity;

    @ManyToOne
    @JoinColumn(name = "subcategory_id")
    private Category subcategory;

    @Size(max = 100)
    private String comment;

    @Size(max = 100)
    private String counterparty;

    @NotNull
    private Boolean isCanceled;

    @Size(max = 38)
    private String correlationId;

}
