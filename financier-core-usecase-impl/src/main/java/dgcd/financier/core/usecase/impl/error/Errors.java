package dgcd.financier.core.usecase.impl.error;

import dgcd.financier.core.usecase.api.error.CommonError;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Errors implements CommonError {

    ACCOUNT_ALREADY_EXISTS("Account with this title already exists"),
    ACCOUNT_NOT_FOUND("Account is not found"),
    ACCOUNT_ALREADY_CLOSED("Account is already closed"),
    ACCOUNT_NOT_EMPTY("Account is not empty"),
    ;

    private final String message;

    public String message() {
        return message;
    }
}
