package dgcd.financier.app.modules.account;

import dgcd.financier.app.modules.account.dto.AccountCreateRequestDto;
import dgcd.financier.app.modules.account.dto.AccountResponseDto;
import dgcd.financier.app.modules.account.exception.AccountWithTitleAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountsService {

    private final AccountsDaoService accountsDaoService;


    @Transactional
    public AccountResponseDto createAccount(AccountCreateRequestDto dto) {
        if (accountsDaoService.findByTitle(dto.title()).isPresent()) {
            throw new AccountWithTitleAlreadyExistsException(dto.title());
        }

        var savedAccount = accountsDaoService.save(dto.makeAccount());
        return AccountResponseDto.of(savedAccount);
    }

}
