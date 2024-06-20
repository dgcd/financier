package dgcd.financier.core.usecase.impl.error;

import dgcd.financier.core.usecase.api.error.CommonError;

public record UsecaseError(
        String message
) implements CommonError {
}
