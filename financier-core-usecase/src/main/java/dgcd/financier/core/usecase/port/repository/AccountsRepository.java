package dgcd.financier.core.usecase.port.repository;

import dgcd.financier.core.domain.Account;

import java.util.List;
import java.util.Optional;

public interface AccountsRepository {

    List<Account> findAll();

    Optional<Account> findByIdentity(Long identity);

    boolean existByTitle(String title);

    Account save(Account account);

    List<Account> saveAll(List<Account> accounts);

}
