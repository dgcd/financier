package dgcd.financier.core.usecase.exception;

public class AccountAlreadyClosedException extends IllegalArgumentException {

    private static final String MSG = "Account with identity '%d' is already closed";

    public AccountAlreadyClosedException(Long identity) {
        super(String.format(MSG, identity));
    }

}
