package dgcd.financier.core.usecase.exception;

public class AccountWithTitleAlreadyExistsException extends IllegalArgumentException {

    private static final String MSG = "Account with title '%s' already exists";

    public AccountWithTitleAlreadyExistsException(String title) {
        super(String.format(MSG, title));
    }

}
