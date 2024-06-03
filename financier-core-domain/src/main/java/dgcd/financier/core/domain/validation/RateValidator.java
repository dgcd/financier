package dgcd.financier.core.domain.validation;

import dgcd.financier.core.domain.model.Rate;

import static dgcd.financier.core.domain.validation.ValidationUtils.checkNonNull;
import static dgcd.financier.core.domain.validation.ValidationUtils.checkPositive;

public class RateValidator {

    public static Rate validate(Rate rate) {
        checkNonNull(rate.getDate(), "date");

        checkNonNull(rate.getUsd(), "usd rate");
        checkPositive(rate.getUsd(), "usd rate");

        checkNonNull(rate.getEur(), "euro rate");
        checkPositive(rate.getEur(), "euro rate");

        return rate;
    }

}
