package dgcd.financier.core.usecase.validator;

import dgcd.financier.core.usecase.CategoryCreateUsecase;

import static java.util.Objects.requireNonNull;

public class CategoryCreateUsecaseRequestValidator {

    public static void validate(CategoryCreateUsecase.Request request) {
        requireNonNull(request, "Request can not be null");
        requireNonNull(request.getTitle(), "Title can not be null");
        // todo: implement more
    }

}
