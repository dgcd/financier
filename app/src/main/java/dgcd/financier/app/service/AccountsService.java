package dgcd.financier.app.service;

import dgcd.financier.app.dto.account.AccountCreateRequestDto;
import dgcd.financier.app.dto.account.AccountResponseDto;
import dgcd.financier.app.service.dao.AccountsDaoService;
import dgcd.financier.app.service.exception.AccountWithTitleAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountsService {

    private final AccountsDaoService accountsDaoService;


    @Transactional
    public AccountResponseDto createAccount(AccountCreateRequestDto dto) {
        var newAccount = dto.makeAccount();
        var dupAccount = accountsDaoService.findByTitle(dto.title());
        if (dupAccount.isPresent()) {
            throw new AccountWithTitleAlreadyExistsException(dto.title());
        }

        var savedAccount = accountsDaoService.save(newAccount);
        return AccountResponseDto.of(savedAccount);
    }

}
