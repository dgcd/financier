package dgcd.financier.core.domain.validation;

import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;
import static java.util.Objects.requireNonNull;

class ValidationUtils {

    static void checkId(Long id, String fieldTitle) {
        requireNonNull(fieldTitle, "field title can not be null");
        if (id != null && id <= 0) {
            throw new IllegalArgumentException(fieldTitle + " must be greater then 0 but was: " + id);
        }
    }


    static void checkNonNull(Object obj, String fieldTitle) {
        requireNonNull(fieldTitle, "field title can not be null");
        if (obj == null)
            throw new IllegalArgumentException(fieldTitle + " can not be null");
    }


    static void checkLength(
            String str,
            int minLength,
            int maxLength,
            String fieldTitle
    ) {
        requireNonNull(fieldTitle, "field title can not be null");
        if (str != null && (str.length() < minLength || str.length() > maxLength)) {
            var msg = String.format("%s length must be from %d to %d but was %d",
                    fieldTitle, minLength, maxLength, str.length());
            throw new IllegalArgumentException(msg);
        }
    }


    static void checkGreaterThenZero(BigDecimal value, String fieldTitle) {
        requireNonNull(fieldTitle, "field title can not be null");
        if (value != null && value.compareTo(ZERO) <= 0) {
            throw new IllegalArgumentException(fieldTitle + " must be greater the 0");
        }
    }

}
