package dgcd.financier.app.modules.operation.exceptions;

import dgcd.financier.app.infrastructure.exception.ServiceException;

public class OperationNotFoundException extends ServiceException {

    private static final String MSG = "Operation with id '%d' is not founds";

    public OperationNotFoundException(Long id) {
        super(String.format(MSG, id));
    }

}
