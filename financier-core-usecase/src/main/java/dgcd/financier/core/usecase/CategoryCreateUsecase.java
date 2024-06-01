package dgcd.financier.core.usecase;

import dgcd.financier.core.domain.Category;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@FunctionalInterface
public interface CategoryCreateUsecase {

    Response execute(Request request);


    @Getter
    @RequiredArgsConstructor
    class Request {

        private final String title;
        private final Long parentIdentity;

    }

    @Getter
    @RequiredArgsConstructor
    class Response {

        private final Category category;

    }

}
