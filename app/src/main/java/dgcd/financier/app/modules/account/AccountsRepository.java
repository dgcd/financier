package dgcd.financier.app.modules.account;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface AccountsRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByTitle(String title);

}
