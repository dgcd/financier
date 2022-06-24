package dgcd.financier.usecase.validator;

import dgcd.financier.usecase.dto.AccountCreateRequestDto;
import dgcd.financier.usecase.exception.ValidationException;

import static java.util.Objects.isNull;

public class AccountValidator {

    private final static int TITLE_MIN_LENGTH = 1;
    private final static int TITLE_MAX_LENGTH = 30;

    public static void validateAccountCreateRequest(AccountCreateRequestDto dto) {
        if (isNull(dto.title())) {
            throw new ValidationException("Title can not be null");
        }

        int titleLength = dto.title().trim().length();
        if (titleLength < TITLE_MIN_LENGTH || titleLength > TITLE_MAX_LENGTH) {
            throw new ValidationException("Account title length must be 1..30");
        }

        if (isNull(dto.currency())) {
            throw new ValidationException("Currency can not be null");
        }
    }

}
