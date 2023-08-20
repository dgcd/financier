package dgcd.financier.core.domain.validation;

import dgcd.financier.core.domain.Rates;

import static java.util.Objects.requireNonNull;

public class RatesValidator {

    public static void validate(Rates rates) {
        requireNonNull(rates.getDate(), "Operation date can not be null");
        // todo: add checks
    }

}
