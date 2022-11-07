package dgcd.financier.repository.entity;

import dgcd.financier.core.domain.Currency;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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

import static dgcd.financier.core.domain.Constants.ACCOUNT_TITLE_MAX_LENGTH;
import static dgcd.financier.core.domain.Constants.ACCOUNT_TITLE_MIN_LENGTH;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "accounts")
public class AccountEntity {

    @Id
    @GeneratedValue(generator = "accounts_id_generator")
    @GenericGenerator(
            name = "accounts_id_generator",
            strategy = "enhanced-sequence",
            parameters = {
                    @Parameter(name = "sequence_name", value = "accounts_id_seq"),
                    @Parameter(name = "increment_size", value = "1"),
                    @Parameter(name = "optimizer", value = "pooled-lo"),
            }
    )
    private Long id;

    @NotNull
    @Size(min = ACCOUNT_TITLE_MIN_LENGTH, max = ACCOUNT_TITLE_MAX_LENGTH)
    private String title;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @NotNull
    private BigDecimal balance;

    @NotNull
    private Boolean isClosed;

}
