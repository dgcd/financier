package dgcd.financier.modules.account;

import dgcd.financier.core.usecase.AccountCloseUsecase;
import dgcd.financier.core.usecase.AccountCreateUsecase;
import dgcd.financier.infrastructure.dto.CommonIdDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
class AccountsService {

    private final AccountCreateUsecase accountCreateUsecase;
    private final AccountCloseUsecase accountCloseUsecase;
    private final AccountMapper accountMapper;


    @Transactional
    public AccountResponseDto createAccount(AccountCreateRequestDto dto) {
        var request = accountMapper.toCreateUsecase(dto);
        var response = accountCreateUsecase.execute(request);
        return accountMapper.fromDomain(response.getAccount());
    }

    @Transactional
    public AccountResponseDto closeAccount(CommonIdDto dto) {
        var request = accountMapper.toCloseUsecase(dto);
        var response = accountCloseUsecase.execute(request);
        return accountMapper.fromDomain(response.getAccount());
    }

}
