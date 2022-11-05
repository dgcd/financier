package dgcd.financier.domain.exception;

import static dgcd.financier.domain.Constants.OPERATION_CORRELATION_MAX_LENGTH;
import static dgcd.financier.domain.Constants.OPERATION_CORRELATION_MIN_LENGTH;

public class IllegalOperationCorrelationException extends IllegalArgumentException {

    private static final String MSG = String.format(
            "Operation correlation id length must be from %d to %d but was: ",
            OPERATION_CORRELATION_MIN_LENGTH,
            OPERATION_CORRELATION_MAX_LENGTH
    );

    public IllegalOperationCorrelationException(String correlation) {
        super(MSG.concat(String.valueOf(correlation.length())));
    }

}
