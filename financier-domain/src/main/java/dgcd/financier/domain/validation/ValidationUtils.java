package dgcd.financier.domain.validation;

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

        int length = string.length();
        if (length < minLength || length > maxLength) {
            throw exceptionGenerator.apply(string);
        }
    }

}
