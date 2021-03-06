package dgcd.financier.app.modules.account;

import dgcd.financier.app.infrastructure.dto.CommonIdDto;
import dgcd.financier.app.modules.account.dto.AccountCreateRequestDto;
import dgcd.financier.app.modules.account.dto.AccountResponseDto;
import dgcd.financier.app.modules.account.exception.AccountClosingException;
import dgcd.financier.app.modules.account.exception.AccountWithTitleAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.math.BigDecimal.ZERO;

@Service
@RequiredArgsConstructor
class AccountsService {

    private final AccountsDaoService accountsDaoService;


    @Transactional
    public AccountResponseDto createAccount(AccountCreateRequestDto dto) {
        if (accountsDaoService.findByTitle(dto.title()).isPresent()) {
            throw new AccountWithTitleAlreadyExistsException(dto.title());
        }

        var account = dto.makeAccount();

        var savedAccount = accountsDaoService.save(account);
        return AccountResponseDto.of(savedAccount);
    }


    @Transactional
    public AccountResponseDto closeAccount(CommonIdDto dto) {
        var account = accountsDaoService.findByIdOrElseThrow(dto.id());
        if (account.getIsClosed()) {
            throw new AccountClosingException("Account with id '" + dto.id() + "' is already closed");
        }
        if (ZERO.compareTo(account.getBalance()) != 0) {
            throw new AccountClosingException("Account with id '" + dto.id() + "' has non-zero balance");
        }

        account.setIsClosed(true);

        var savedAccount = accountsDaoService.save(account);
        return AccountResponseDto.of(savedAccount);
    }

}
