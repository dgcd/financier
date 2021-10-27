package dgcd.financier.app.domain.repo;

import dgcd.financier.app.domain.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountsRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByTitle(String title);

}
