package dgcd.financier.domain.exception;

import static dgcd.financier.domain.Constants.OPERATION_COUNTERPARTY_MAX_LENGTH;
import static dgcd.financier.domain.Constants.OPERATION_COUNTERPARTY_MIN_LENGTH;

public class IllegalOperationCounterpartyException extends IllegalArgumentException {

    private static final String MSG = String.format(
            "Operation counterparty length must be from %d to %d but was: ",
            OPERATION_COUNTERPARTY_MIN_LENGTH,
            OPERATION_COUNTERPARTY_MAX_LENGTH
    );

    public IllegalOperationCounterpartyException(String counterparty) {
        super(MSG.concat(String.valueOf(counterparty.length())));
    }

}
