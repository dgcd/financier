package dgcd.financier.core.usecase.validator;

import dgcd.financier.core.usecase.OperationCancelUsecase;

import static java.util.Objects.requireNonNull;

public class OperationCancelUsecaseRequestValidator {

    public static void validate(OperationCancelUsecase.Request request) {
        requireNonNull(request, "Request can not be null");
        requireNonNull(request.getOperationIdentity(), "Operation identity can not be null");
        // todo extract
        if (request.getOperationIdentity().compareTo(1L) <= 0) {
            throw new IllegalArgumentException("Identity can not be less then 1");
        }

        // todo more checks
    }

}
