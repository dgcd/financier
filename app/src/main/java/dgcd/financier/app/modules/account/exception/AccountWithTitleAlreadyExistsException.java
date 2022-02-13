package dgcd.financier.app.modules.account.exception;

import dgcd.financier.app.commons.service.exception.ServiceException;

public class AccountWithTitleAlreadyExistsException extends ServiceException {

    private static final String MSG = "Account with title '%s' already exists";

    public AccountWithTitleAlreadyExistsException(String title) {
        super(String.format(MSG, title));
    }

}
