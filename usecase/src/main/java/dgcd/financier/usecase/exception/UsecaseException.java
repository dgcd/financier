package dgcd.financier.usecase.exception;

public class UsecaseException extends RuntimeException {

    public UsecaseException(String message) {
        super(message);
    }

    public UsecaseException(String message, Throwable cause) {
        super(message, cause);
    }

}
