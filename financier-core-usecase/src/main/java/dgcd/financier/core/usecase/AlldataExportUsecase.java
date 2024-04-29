package dgcd.financier.core.usecase;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@FunctionalInterface
public interface AlldataExportUsecase extends AlldataUsecase {

    Response execute();


    @Getter
    @RequiredArgsConstructor
    class Response {

        private final AlldataRows alldataRows;

    }

}
