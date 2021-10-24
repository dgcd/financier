package dgcd.financier.app.service.dao;

import dgcd.financier.app.domain.model.Account;
import dgcd.financier.app.domain.model.AccountsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountsDaoService {

    private final AccountsRepository accountsRepository;


    public List<Account> findAll() {
        return accountsRepository.findAll();
    }

}
