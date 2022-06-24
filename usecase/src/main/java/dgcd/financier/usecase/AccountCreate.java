package dgcd.financier.usecase;

import dgcd.financier.usecase.dto.AccountCreateRequestDto;
import dgcd.financier.usecase.dto.AccountResponseDto;
import dgcd.financier.usecase.exception.account.AccountWithTitleAlreadyExistsException;
import dgcd.financier.usecase.mappers.AccountMapper;
import dgcd.financier.usecase.port.AccountsRepository;
import dgcd.financier.usecase.validator.AccountValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import static java.math.BigDecimal.ZERO;

@RequiredArgsConstructor
public class AccountCreate {

    private final AccountsRepository accountsRepository;

    @Transactional
    public AccountResponseDto createAccount(AccountCreateRequestDto dto) {
        AccountValidator.validateAccountCreateRequest(dto);

        if (accountsRepository.findByTitle(dto.title()).isPresent()) {
            throw new AccountWithTitleAlreadyExistsException(dto.title());
        }

        var account = AccountMapper.INSTANCE.accountCreateRequestDtoToAccount(dto);
        account.setBalance(ZERO);
        account.setIsClosed(false);

        var savedAccount = accountsRepository.save(account);

        return AccountMapper.INSTANCE.accountToResponseDto(savedAccount);
    }

}
