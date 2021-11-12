package dgcd.financier.app.service.dao;

import dgcd.financier.app.domain.model.Account;
import dgcd.financier.app.domain.repo.AccountsRepository;
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

    public Account save(Account account) {
        return accountsRepository.save(account);
    }

    public Optional<Account> findByTitle(String title) {
        return accountsRepository.findByTitle(title);
    }

}
