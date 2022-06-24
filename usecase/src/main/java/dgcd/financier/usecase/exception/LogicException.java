package dgcd.financier.usecase.exception;

public class LogicException extends UsecaseException {

    public LogicException(String message) {
        super(message);
    }

    public LogicException(String message, Throwable cause) {
        super(message, cause);
    }

}
