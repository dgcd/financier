package dgcd.financier.domain.exception;

import static dgcd.financier.domain.Constants.ACCOUNT_TITLE_MAX_LENGTH;
import static dgcd.financier.domain.Constants.ACCOUNT_TITLE_MIN_LENGTH;

public class IllegalAccountTitleException extends IllegalArgumentException {

    private static final String MSG = String.format(
            "Account title length must be from %d to %d but was: ",
            ACCOUNT_TITLE_MIN_LENGTH,
            ACCOUNT_TITLE_MAX_LENGTH
    );

    public IllegalAccountTitleException(String title) {
        super(MSG.concat(String.valueOf(title.length())));
    }

}
