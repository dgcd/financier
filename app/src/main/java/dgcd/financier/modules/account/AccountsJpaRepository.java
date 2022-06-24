package dgcd.financier.modules.account;

import dgcd.financier.domain.entity.Account;
import dgcd.financier.usecase.port.AccountsRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountsJpaRepository extends JpaRepository<Account, Long>, AccountsRepository {

    Optional<Account> findByTitle(String title);

}
