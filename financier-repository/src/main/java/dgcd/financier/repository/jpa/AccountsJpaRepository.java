package dgcd.financier.repository.jpa;

import dgcd.financier.repository.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountsJpaRepository extends JpaRepository<AccountEntity, Long> {
}
