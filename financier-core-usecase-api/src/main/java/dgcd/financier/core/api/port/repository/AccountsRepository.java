package dgcd.financier.core.api.port.repository;

import dgcd.financier.core.domain.model.Account;

import java.util.List;

public interface AccountsRepository {

    List<Account> findAll();

//    Optional<Account> findById(Long id);

    boolean existByTitle(String title);

    Account save(Account account);

//    List<Account> saveAll(List<Account> accounts);

}
