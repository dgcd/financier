package dgcd.financier.core.usecase.validator;

import dgcd.financier.core.usecase.AccountCreateUsecase;

import static java.util.Objects.requireNonNull;

public class AccountCreateUsecaseRequestValidator {

    public static void validate(AccountCreateUsecase.Request request) {
        requireNonNull(request, "Request can not be null");
        requireNonNull(request.getTitle(), "Title can not be null");
        requireNonNull(request.getCurrency(), "Currency can not be null");
        // todo: implement more
    }

}
