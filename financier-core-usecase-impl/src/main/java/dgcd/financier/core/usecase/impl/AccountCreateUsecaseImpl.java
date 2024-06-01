package dgcd.financier.core.usecase.impl;

import dgcd.financier.core.api.AccountCreateUsecase;
import dgcd.financier.core.api.dto.AccountCreateRequestDto;
import dgcd.financier.core.api.dto.common.AccountDto;
import dgcd.financier.core.api.error.CommonError;
import dgcd.financier.core.api.port.repository.AccountsRepository;
import dgcd.financier.core.domain.model.Account;
import dgcd.financier.core.usecase.impl.mapper.AccountMapper;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;

import static dgcd.financier.core.api.utils.EitherUtils.toRight;
import static dgcd.financier.core.usecase.impl.error.Errors.ACCOUNT_ALREADY_EXISTS;
import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;

@RequiredArgsConstructor
public class AccountCreateUsecaseImpl implements AccountCreateUsecase {

    private final AccountsRepository accountsRepository;

    @Override
    public Either<CommonError, AccountDto> execute(AccountCreateRequestDto request) {
        return toRight(request)
                .flatMap(this::createAccount)
                .flatMap(this::checkNotExists)
                .map(accountsRepository::save)
                .map(AccountMapper.INSTANCE::fromDomain);
    }


    private Either<CommonError, Account> createAccount(AccountCreateRequestDto request) {
        var account = new Account()
                .setTitle(request.title())
                .setCurrency(request.currency());
        return toRight(account);
    }


    private Either<CommonError, Account> checkNotExists(Account account) {
        return accountsRepository.existByTitle(account.getTitle()) ?
                left(ACCOUNT_ALREADY_EXISTS) :
                right(account);
    }

}
