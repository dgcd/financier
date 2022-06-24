package dgcd.financier.usecase.exception;

public class ValidationException extends UsecaseException {

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }

}
