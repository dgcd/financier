package dgcd.financier.core.usecase.impl;

import dgcd.financier.core.domain.Account;
import dgcd.financier.core.domain.factory.AccountFactory;
import dgcd.financier.core.usecase.AccountCloseUsecase;
import dgcd.financier.core.usecase.exception.AccountAlreadyClosedException;
import dgcd.financier.core.usecase.exception.AccountHasNonZeroBalanceException;
import dgcd.financier.core.usecase.exception.AccountNotExistsException;
import dgcd.financier.core.usecase.port.repository.AccountsRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static dgcd.financier.core.usecase.validator.AccountCloseUsecaseRequestValidator.validate;
import static java.math.BigDecimal.ZERO;

@RequiredArgsConstructor
public class AccountCloseUsecaseImpl implements AccountCloseUsecase {

    private final AccountsRepository accountsRepository;


    @Override
    public AccountCloseUsecase.Response execute(Request request) {
        validate(request);

        var accountOptional = accountsRepository.findByIdentity(request.getIdentity());
        var accountFromDb = checkAccount(request.getIdentity(), accountOptional);

        var closedAccount = AccountFactory.makeWithSetClosed(accountFromDb);

        var savedAccount = accountsRepository.save(closedAccount);
        return new Response(savedAccount);
    }


    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    private static Account checkAccount(Long identity, Optional<Account> accountOptional) {
        if (accountOptional.isEmpty()) {
            throw new AccountNotExistsException(identity);
        }
        var accountFromDb = accountOptional.get();
        if (accountFromDb.getIsClosed()) {
            throw new AccountAlreadyClosedException(accountFromDb.getIdentity());
        }
        if (ZERO.compareTo(accountFromDb.getBalance()) != 0) {
            throw new AccountHasNonZeroBalanceException(accountFromDb.getIdentity());
        }
        return accountFromDb;
    }

}
