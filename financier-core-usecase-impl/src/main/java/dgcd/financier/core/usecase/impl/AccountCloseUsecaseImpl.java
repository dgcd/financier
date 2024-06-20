package dgcd.financier.core.usecase.impl;

import dgcd.financier.core.domain.model.Account;
import dgcd.financier.core.usecase.api.AccountCloseUsecase;
import dgcd.financier.core.usecase.api.dto.common.AccountDto;
import dgcd.financier.core.usecase.api.error.CommonError;
import dgcd.financier.core.usecase.api.port.repository.AccountsRepository;
import dgcd.financier.core.usecase.api.utils.EitherUtils;
import dgcd.financier.core.usecase.impl.mapper.AccountMapper;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;

import static dgcd.financier.core.usecase.api.utils.EitherUtils.toRight;
import static dgcd.financier.core.usecase.impl.error.Errors.ACCOUNT_ALREADY_CLOSED;
import static dgcd.financier.core.usecase.impl.error.Errors.ACCOUNT_NOT_EMPTY;
import static dgcd.financier.core.usecase.impl.error.Errors.ACCOUNT_NOT_FOUND;
import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;
import static java.math.BigDecimal.ZERO;

@RequiredArgsConstructor
public class AccountCloseUsecaseImpl implements AccountCloseUsecase {

    private final AccountsRepository accountsRepository;

    @Override
    public Either<CommonError, AccountDto> execute(Long id) {
        return toRight(id)
                .flatMap(this::findAccount)
                .flatMap(this::checkClosed)
                .flatMap(this::checkBalance)
                .map(account -> account.setClosed(true))
                .map(accountsRepository::update)
                .map(AccountMapper.INSTANCE::fromDomain);
    }

    private Either<CommonError, Account> findAccount(Long id) {
        return accountsRepository.findById(id)
                .map(EitherUtils::toRight)
                .orElseGet(() -> left(ACCOUNT_NOT_FOUND));
    }

    private Either<CommonError, Account> checkClosed(Account account) {
        return account.isClosed() ?
                left(ACCOUNT_ALREADY_CLOSED) :
                right(account);
    }

    private Either<CommonError, Account> checkBalance(Account account) {
        return account.getBalance().compareTo(ZERO) != 0 ?
                left(ACCOUNT_NOT_EMPTY) :
                right(account);
    }

}
