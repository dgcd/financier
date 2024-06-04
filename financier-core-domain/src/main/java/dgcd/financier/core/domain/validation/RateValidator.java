package dgcd.financier.core.domain.validation;

import dgcd.financier.core.domain.model.Rate;

import static dgcd.financier.core.domain.Constants.AMOUNT_SCALE;
import static dgcd.financier.core.domain.validation.ValidationUtils.checkMaxScale;
import static dgcd.financier.core.domain.validation.ValidationUtils.checkNonNull;
import static dgcd.financier.core.domain.validation.ValidationUtils.checkPositive;

public class RateValidator {

    public static Rate validate(Rate rate) {
        checkNonNull(rate.getDate(), "date");

        checkNonNull(rate.getUsd(), "usd rate");
        checkPositive(rate.getUsd(), "usd rate");
        checkMaxScale(rate.getUsd(), AMOUNT_SCALE, "usd rate");

        checkNonNull(rate.getEur(), "euro rate");
        checkPositive(rate.getEur(), "euro rate");
        checkMaxScale(rate.getEur(), AMOUNT_SCALE, "euro rate");

        return rate;
    }

}
