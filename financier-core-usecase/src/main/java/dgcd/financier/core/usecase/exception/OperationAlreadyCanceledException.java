package dgcd.financier.core.usecase.exception;

public class OperationAlreadyCanceledException extends IllegalArgumentException {

    private static final String MSG = "Operation with id '%d' is already canceled";

    public OperationAlreadyCanceledException(Long identity) {
        super(String.format(MSG, identity));
    }

}
