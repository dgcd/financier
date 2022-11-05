package dgcd.financier.core.domain.exception;

import dgcd.financier.core.domain.Constants;

public class IllegalOperationCorrelationException extends IllegalArgumentException {

    private static final String MSG = String.format(
            "Operation correlation id length must be from %d to %d but was: ",
            Constants.OPERATION_CORRELATION_MIN_LENGTH,
            Constants.OPERATION_CORRELATION_MAX_LENGTH
    );

    public IllegalOperationCorrelationException(String correlation) {
        super(MSG.concat(String.valueOf(correlation.length())));
    }

}
