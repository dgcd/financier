package dgcd.financier.core.usecase.exception;

public class OperationNotExistsException extends IllegalArgumentException {

    private static final String MSG = "Operation with identity '%d' doesn't exist";

    public OperationNotExistsException(Long identity) {
        super(String.format(MSG, identity));
    }

}
