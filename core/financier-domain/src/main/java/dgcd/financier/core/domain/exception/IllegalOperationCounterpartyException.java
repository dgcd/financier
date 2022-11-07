package dgcd.financier.core.domain.exception;

import dgcd.financier.core.domain.Constants;

public class IllegalOperationCounterpartyException extends IllegalArgumentException {

    private static final String MSG = String.format(
            "Operation counterparty length must be from %d to %d but was: ",
            Constants.OPERATION_COUNTERPARTY_MIN_LENGTH,
            Constants.OPERATION_COUNTERPARTY_MAX_LENGTH
    );

    public IllegalOperationCounterpartyException(String counterparty) {
        super(MSG.concat(String.valueOf(counterparty.length())));
    }

}
