package dgcd.financier.core.usecase;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public interface AlldataExportUsecase extends AlldataUsecase<AlldataExportUsecase.Request, AlldataExportUsecase.Response> {

    @Override
    AlldataExportUsecase.Response execute(AlldataExportUsecase.Request request);


    @Getter
    @RequiredArgsConstructor
    class Request implements AlldataUsecase.Request {
    }

    @Getter
    @RequiredArgsConstructor
    class Response implements AlldataUsecase.Response {

        private final AlldataRows alldataRows;

    }

}
