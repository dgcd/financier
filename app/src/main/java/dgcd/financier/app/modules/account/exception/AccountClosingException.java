package dgcd.financier.app.modules.account.exception;

import dgcd.financier.app.commons.service.exception.ServiceException;

public class AccountClosingException extends ServiceException {

    public AccountClosingException(String msg) {
        super(msg);
    }

}
