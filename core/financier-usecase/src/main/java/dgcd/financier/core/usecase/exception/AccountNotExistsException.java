package dgcd.financier.core.usecase.exception;

public class AccountNotExistsException extends IllegalArgumentException {

    private static final String MSG = "Account with identity '%d' doesn't exist";

    public AccountNotExistsException(Long identity) {
        super(String.format(MSG, identity));
    }

}
