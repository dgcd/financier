package dgcd.financier.core.usecase.impl.error;

import dgcd.financier.core.api.error.CommonError;

public record UsecaseError(
        String message
) implements CommonError {
}
