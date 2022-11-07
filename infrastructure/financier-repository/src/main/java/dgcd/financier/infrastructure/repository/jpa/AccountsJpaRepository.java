package dgcd.financier.infrastructure.repository.jpa;

import dgcd.financier.infrastructure.repository.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountsJpaRepository extends JpaRepository<AccountEntity, Long> {

    Optional<AccountEntity> findByTitle(String title);

}
