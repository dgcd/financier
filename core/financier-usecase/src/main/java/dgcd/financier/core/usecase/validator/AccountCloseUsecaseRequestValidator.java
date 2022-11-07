package dgcd.financier.core.usecase.validator;

import dgcd.financier.core.usecase.AccountCloseUsecase;

import static java.util.Objects.requireNonNull;

public class AccountCloseUsecaseRequestValidator {

    public static void validate(AccountCloseUsecase.Request request) {
        requireNonNull(request, "Request can not be null");
        requireNonNull(request.getIdentity(), "Identity can not be null");
        // todo: implement more
    }

}
