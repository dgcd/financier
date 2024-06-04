package dgcd.financier.core.usecase.impl.error;

import dgcd.financier.core.usecase.api.error.CommonError;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Errors implements CommonError {

    ACCOUNT_ALREADY_EXISTS("Account with this title already exists"),
    ACCOUNT_NOT_FOUND("Account is not found"),
    ACCOUNT_ALREADY_CLOSED("Account is already closed"),
    ACCOUNT_CLOSED("Account is closed"),
    ACCOUNT_NOT_EMPTY("Account is not empty"),

    CATEGORY_ALREADY_EXISTS("Category with this title already exists"),
    CATEGORY_PARENT_NOT_FOUND("Parent category is not found"),
    CATEGORY_CAN_NOT_BE_PARENT("Subcategory can not be parent"),
    CATEGORY_NOT_FOUND("Subcategory is not found"),

    CURRENCIES_DIFFER("Currencies must match"),
    CURRENCIES_MATCH("Currencies must differ"),

    OPERATION_NOT_FOUND("Operation is not found"),
    TOO_MANY_OPERATIONS_FOUND("Too many operations found"),
    OPERATION_ALREADY_CANCELED("Operation is already canceled"),
    ;

    private final String message;

    public String message() {
        return message;
    }
}
