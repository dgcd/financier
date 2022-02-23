package dgcd.financier.app.modules.operation.exceptions;

import dgcd.financier.app.commons.service.exception.ServiceException;

public class OperationCreateException extends ServiceException {

    public OperationCreateException(String msg) {
        super(msg);
    }

}
