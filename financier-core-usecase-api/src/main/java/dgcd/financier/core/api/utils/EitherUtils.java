package dgcd.financier.core.api.utils;

import dgcd.financier.core.api.error.CommonError;
import io.vavr.control.Either;

public class EitherUtils {

    public static <T> Either<CommonError, T> toRight(T value) {
        return Either.right(value);
    }

}
