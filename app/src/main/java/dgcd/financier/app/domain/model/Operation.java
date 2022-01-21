package dgcd.financier.app.domain.model;

import dgcd.financier.app.domain.dict.OperationType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
