package dgcd.financier.usecase.exception.account;

import dgcd.financier.usecase.exception.LogicException;

public class AccountWithTitleAlreadyExistsException extends LogicException {

    private static final String MSG = "Account with title '%s' already exists";

    public AccountWithTitleAlreadyExistsException(String title) {
        super(String.format(MSG, title));
    }

}
