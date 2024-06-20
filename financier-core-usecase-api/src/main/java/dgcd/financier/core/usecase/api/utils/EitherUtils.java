package dgcd.financier.core.usecase.api.utils;

import dgcd.financier.core.usecase.api.error.CommonError;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;

public class EitherUtils {

    public static <T> Either<CommonError, T> toRight(T value) {
        return Either.right(value);
    }

    public static <T> Either<CommonError, T> toLeft(Throwable throwable) {
        return Either.left(new Err(throwable.getMessage()));
    }


    @RequiredArgsConstructor
    private static class Err implements CommonError {

        private final String message;

        @Override
        public String message() {
            return message;
        }

    }

}
