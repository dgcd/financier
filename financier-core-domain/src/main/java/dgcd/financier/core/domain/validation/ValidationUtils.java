package dgcd.financier.core.domain.validation;

import dgcd.financier.core.domain.exception.IllegalIdentityException;

import java.util.function.Function;

import static java.util.Objects.isNull;

public class ValidationUtils {

    public static void checkStringBoundaries(
            String string,
            int minLength,
            int maxLength,
            Function<String, RuntimeException> exceptionGenerator
    ) {
        if (isNull(string)) {
            return;
        }
        if (string.length() < minLength || string.length() > maxLength) {
            throw exceptionGenerator.apply(string);
        }
    }


    public static void checkIdentity(Long identity) {
        if (isNull(identity)) {
            return;
        }
        if (identity <= 0) {
            throw new IllegalIdentityException(identity);
        }
    }

}
