package dgcd.financier.app.modules.account;

import dgcd.financier.app.modules.account.exception.AccountNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountsDaoService {

    private final AccountsRepository accountsRepository;


    public List<Account> findAll() {
        return accountsRepository.findAll();
    }

    Account save(Account account) {
        return accountsRepository.save(account);
    }

    Optional<Account> findByTitle(String title) {
        return accountsRepository.findByTitle(title);
    }

    public Account findByIdOrElseThrow(Long accountId) {
        return accountsRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(accountId));
    }

}
