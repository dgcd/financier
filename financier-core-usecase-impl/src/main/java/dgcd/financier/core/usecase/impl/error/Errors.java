package dgcd.financier.core.usecase.impl.error;

import dgcd.financier.core.api.error.CommonError;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Errors implements CommonError {

    ACCOUNT_ALREADY_EXISTS("Account with this title already exists"),
    ;

    private final String message;

    public String message() {
        return message;
    }
}
