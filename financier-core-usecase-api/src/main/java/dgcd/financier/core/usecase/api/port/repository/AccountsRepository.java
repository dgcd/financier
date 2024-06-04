package dgcd.financier.core.usecase.api.port.repository;

import dgcd.financier.core.domain.model.Account;

import java.util.List;
import java.util.Optional;

public interface AccountsRepository {

    List<Account> findAll();

    Optional<Account> findById(Long id);

    boolean existByTitle(String title);

    Account create(Account account);

    Account update(Account account);

//    List<Account> saveAll(List<Account> accounts);

}
