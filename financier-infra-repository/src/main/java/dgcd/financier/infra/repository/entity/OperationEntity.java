package dgcd.financier.infra.repository.entity;

import dgcd.financier.core.domain.OperationType;
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

import java.math.BigDecimal;
import java.time.LocalDate;

import static dgcd.financier.core.domain.Constants.OPERATION_COMMENT_MAX_LENGTH;
import static dgcd.financier.core.domain.Constants.OPERATION_COMMENT_MIN_LENGTH;
import static dgcd.financier.core.domain.Constants.OPERATION_CORRELATION_MAX_LENGTH;
import static dgcd.financier.core.domain.Constants.OPERATION_CORRELATION_MIN_LENGTH;
import static dgcd.financier.core.domain.Constants.OPERATION_COUNTERPARTY_MAX_LENGTH;
import static dgcd.financier.core.domain.Constants.OPERATION_COUNTERPARTY_MIN_LENGTH;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "operations")
public class OperationEntity {

    @Id
    @GeneratedValue(generator = "operations_id_generator")
    private Long id;

    @Column(name = "op_date", updatable = false, nullable = false)
    private LocalDate date;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "account_id")
    private AccountEntity account;

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
    private CategoryEntity subcategory;

    @Size(min = OPERATION_COMMENT_MIN_LENGTH, max = OPERATION_COMMENT_MAX_LENGTH)
    private String comment;

    @Size(min = OPERATION_COUNTERPARTY_MIN_LENGTH, max = OPERATION_COUNTERPARTY_MAX_LENGTH)
    private String counterparty;

    @NotNull
    private Boolean isCanceled;

    @Size(min = OPERATION_CORRELATION_MIN_LENGTH, max = OPERATION_CORRELATION_MAX_LENGTH)
    private String correlationId;

}
