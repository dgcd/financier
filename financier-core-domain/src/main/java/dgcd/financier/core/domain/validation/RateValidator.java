package dgcd.financier.core.domain.validation;

import dgcd.financier.core.domain.model.Rate;

import static dgcd.financier.core.domain.validation.ValidationUtils.checkGreaterThenZero;
import static dgcd.financier.core.domain.validation.ValidationUtils.checkNonNull;

public class RateValidator {

    public static Rate validate(Rate rate) {
        checkNonNull(rate.getDate(), "date");

        checkNonNull(rate.getUsd(), "usd rate");
        checkGreaterThenZero(rate.getUsd(), "usd rate");

        checkNonNull(rate.getEur(), "euro rate");
        checkGreaterThenZero(rate.getEur(), "euro rate");

        return rate;
    }

}
