package dgcd.financier.app.modules.operation.exceptions;

import dgcd.financier.app.infrastructure.exception.ServiceException;

public class OperationCreateException extends ServiceException {

    public OperationCreateException(String msg) {
        super(msg);
    }

}
