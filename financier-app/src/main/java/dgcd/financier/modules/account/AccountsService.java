package dgcd.financier.modules.account;

import dgcd.financier.core.usecase.AccountCreateUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
class AccountsService {

    private final AccountCreateUsecase accountCreateUsecase;
    private final AccountMapper accountMapper;


    @Transactional
    public AccountResponseDto createAccount(AccountCreateRequestDto dto) {
        var request = accountMapper.toUsecase(dto);
        var response = accountCreateUsecase.execute(request);
        return accountMapper.fromDomain(response.getAccount());
    }

//    @Transactional
//    public AccountResponseDto closeAccount(CommonIdDto dto) {
//        var account = accountsDaoService.findByIdOrElseThrow(dto.id());
//        if (account.getIsClosed()) {
//            throw new AccountClosingException("Account with id '" + dto.id() + "' is already closed");
//        }
//        if (ZERO.compareTo(account.getBalance()) != 0) {
//            throw new AccountClosingException("Account with id '" + dto.id() + "' has non-zero balance");
//        }
//
//        account.setIsClosed(true);
//
//        var savedAccount = accountsDaoService.save(account);
//        return AccountResponseDto.of(savedAccount);
//    }

}
