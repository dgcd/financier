package dgcd.financier.app.modules.account.exception;

import dgcd.financier.app.infrastructure.exception.ServiceException;

public class AccountNotFoundException extends ServiceException {

    private static final String MSG = "Account with id '%d' is not founds";

    public AccountNotFoundException(Long id) {
        super(String.format(MSG, id));
    }

}
