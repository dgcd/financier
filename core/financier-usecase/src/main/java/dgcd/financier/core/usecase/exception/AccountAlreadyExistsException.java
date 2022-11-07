package dgcd.financier.core.usecase.exception;

public class AccountAlreadyExistsException extends IllegalArgumentException {

    private static final String MSG = "Account with title '%s' already exists";

    public AccountAlreadyExistsException(String title) {
        super(String.format(MSG, title));
    }

}
