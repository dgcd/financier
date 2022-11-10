package dgcd.financier.infra.gateway.exception;

public class ServiceException extends RuntimeException {

    public ServiceException(String message) {
        super(message);
    }

}
