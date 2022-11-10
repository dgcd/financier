package dgcd.financier.core.usecase.impl;

import dgcd.financier.core.domain.factory.AccountFactory;
import dgcd.financier.core.usecase.AccountCreateUsecase;
import dgcd.financier.core.usecase.exception.AccountAlreadyExistsException;
import dgcd.financier.core.usecase.port.repository.AccountsRepository;
import lombok.RequiredArgsConstructor;

import static dgcd.financier.core.usecase.validator.AccountCreateUsecaseRequestValidator.validate;

@RequiredArgsConstructor
public class AccountCreateUsecaseImpl implements AccountCreateUsecase {

    private final AccountsRepository accountsRepository;

    @Override
    public AccountCreateUsecase.Response execute(AccountCreateUsecase.Request request) {
        validate(request);

        if (accountsRepository.existByTitle(request.getTitle())) {
            throw new AccountAlreadyExistsException(request.getTitle());
        }

        var account = AccountFactory.makeNew(request.getTitle(), request.getCurrency());

        var savedAccount = accountsRepository.save(account);
        return new Response(savedAccount);
    }

}
