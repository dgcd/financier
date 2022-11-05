package dgcd.financier.core.usecase.port.repository;

import dgcd.financier.core.domain.Account;

import java.util.List;

public interface AccountsRepository {

    List<Account> findAll();

}
