package dgcd.financier.usecase.port;

import dgcd.financier.domain.entity.Account;

import java.util.Optional;

public interface AccountsRepository {

    Optional<Account> findByTitle(String title);

    Account save(Account account);

}
