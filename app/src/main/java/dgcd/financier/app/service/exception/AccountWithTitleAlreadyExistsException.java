package dgcd.financier.app.service.exception;

public class AccountWithTitleAlreadyExistsException extends ServiceException {

    private static final String MSG = "Account with title '%s' already exists";

    public AccountWithTitleAlreadyExistsException(String title) {
        super(String.format(MSG, title));
    }

}
