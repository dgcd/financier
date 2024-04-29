package dgcd.financier.core.usecase;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@FunctionalInterface
public interface AlldataImportUsecase extends AlldataUsecase {

    Response execute(Request request);


    @Getter
    @RequiredArgsConstructor
    class Request {

        private final AlldataRows alldataRows;

    }

    @Getter
    @RequiredArgsConstructor
    class Response {
    }

}
