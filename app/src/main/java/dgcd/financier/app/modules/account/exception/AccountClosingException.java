package dgcd.financier.app.modules.account.exception;

import dgcd.financier.app.infrastructure.exception.ServiceException;

public class AccountClosingException extends ServiceException {

    public AccountClosingException(String msg) {
        super(msg);
    }

}
