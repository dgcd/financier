package dgcd.financier.core.usecase;

import dgcd.financier.core.domain.Category;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public interface CategoryCreateUsecase extends Usecase<CategoryCreateUsecase.Request, CategoryCreateUsecase.Response> {

    @Override
    CategoryCreateUsecase.Response execute(CategoryCreateUsecase.Request request);


    @Getter
    @RequiredArgsConstructor
    class Request implements Usecase.Request {

        private final String title;
        private final Long parentIdentity;

    }

    @Getter
    @RequiredArgsConstructor
    class Response implements Usecase.Response {

        private final Category category;

    }

}
