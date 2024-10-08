package dgcd.financier.core.usecase.impl;

import dgcd.financier.core.domain.model.Account;
import dgcd.financier.core.usecase.api.AccountCreateUsecase;
import dgcd.financier.core.usecase.api.dto.AccountCreateRequestDto;
import dgcd.financier.core.usecase.api.dto.common.AccountDto;
import dgcd.financier.core.usecase.api.error.CommonError;
import dgcd.financier.core.usecase.api.port.repository.AccountsRepository;
import dgcd.financier.core.usecase.api.utils.EitherUtils;
import dgcd.financier.core.usecase.impl.mapper.AccountMapper;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;

import static dgcd.financier.core.usecase.api.utils.EitherUtils.toRight;
import static dgcd.financier.core.usecase.impl.error.Errors.ACCOUNT_ALREADY_EXISTS;
import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;

@RequiredArgsConstructor
public class AccountCreateUsecaseImpl implements AccountCreateUsecase {

    private final AccountsRepository accountsRepository;

    @Override
    public Either<CommonError, AccountDto> execute(AccountCreateRequestDto request) {
        return toRight(request)
                .flatMap(this::checkNotExists)
                .flatMap(this::createAccount)
                .map(accountsRepository::create)
                .map(AccountMapper.INSTANCE::fromDomain);
    }


    private Either<CommonError, AccountCreateRequestDto> checkNotExists(AccountCreateRequestDto request) {
        return accountsRepository.existByTitle(request.title()) ?
                left(ACCOUNT_ALREADY_EXISTS) :
                right(request);
    }


    private Either<CommonError, Account> createAccount(AccountCreateRequestDto request) {
        return Try.of(() -> createAndValidate(request))
                .fold(EitherUtils::toLeft, EitherUtils::toRight);
    }


    private static Account createAndValidate(AccountCreateRequestDto request) {
        return new Account()
                .setTitle(request.title())
                .setCurrency(request.currency())
                .validate();
    }

}
