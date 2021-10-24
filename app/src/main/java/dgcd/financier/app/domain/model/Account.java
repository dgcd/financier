package dgcd.financier.app.domain.model;

import dgcd.financier.app.domain.dict.Currency;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    private Long id;

    private String title;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    private BigDecimal balance;

}
