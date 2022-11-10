package dgcd.financier.core.domain.exception;

import dgcd.financier.core.domain.Constants;

public class IllegalAccountTitleException extends IllegalArgumentException {

    private static final String MSG = String.format(
            "Account title length must be from %d to %d but was: ",
            Constants.ACCOUNT_TITLE_MIN_LENGTH,
            Constants.ACCOUNT_TITLE_MAX_LENGTH
    );

    public IllegalAccountTitleException(String title) {
        super(MSG.concat(String.valueOf(title.length())));
    }

}
