package dgcd.financier.core.usecase;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public interface AlldataImportUsecase extends AlldataUsecase<AlldataImportUsecase.Request, AlldataImportUsecase.Response> {

    @Override
    AlldataImportUsecase.Response execute(AlldataImportUsecase.Request request);


    @Getter
    @RequiredArgsConstructor
    class Request implements AlldataUsecase.Request {

        private final AlldataRows alldataRows;

    }

    @Getter
    @RequiredArgsConstructor
    class Response implements AlldataUsecase.Response {
    }

}
