package dgcd.financier.app.service;

import dgcd.financier.app.domain.model.Account;
import dgcd.financier.app.service.dao.AccountsDaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountsService {

    private final AccountsDaoService accountsDaoService;


    @Transactional(readOnly = true)
    public List<Account> getAccounts() {
        return accountsDaoService.findAll();
    }

}
