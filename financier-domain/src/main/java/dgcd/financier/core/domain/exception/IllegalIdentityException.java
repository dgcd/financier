package dgcd.financier.core.domain.exception;

public class IllegalIdentityException extends IllegalArgumentException {

    private static final String MSG = "Identity must be greater then 0 but was: ";

    public IllegalIdentityException(Long identity) {
        super(MSG.concat(String.valueOf(identity)));
    }

}
