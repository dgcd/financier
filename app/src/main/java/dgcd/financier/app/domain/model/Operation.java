package dgcd.financier.app.domain.model;

import dgcd.financier.app.domain.dict.OperationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
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
    private Long id;

    @Column(name = "op_date", updatable = false, nullable = false)
    private LocalDate date;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @NotNull
    private BigDecimal amount;

    @NotNull
    private Float quantity;

    @NotNull
    @Column(name = "op_type", nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private OperationType type;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Max(100)
    private String counterparty;

    @Max(100)
    private String comment;

}
