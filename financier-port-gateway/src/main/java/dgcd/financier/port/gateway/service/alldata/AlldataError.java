package dgcd.financier.port.gateway.service.alldata;

import dgcd.financier.core.usecase.api.error.CommonError;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AlldataError implements CommonError {

    private final String message;

    public static AlldataError of(Throwable t) {
        return new AlldataError(t.getMessage());
    }

    @Override
    public String message() {
        return message;
    }

}
