package dgcd.financier.core.usecase.impl.utils;

import dgcd.financier.core.api.error.CommonError;
import io.vavr.control.Either;

public class EitherUtils {

    public static <T> Either<CommonError, T> toRight(T value) {
        return Either.right(value);
    }

}
