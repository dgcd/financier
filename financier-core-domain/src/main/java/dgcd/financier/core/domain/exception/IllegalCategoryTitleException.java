package dgcd.financier.core.domain.exception;

import dgcd.financier.core.domain.Constants;

public class IllegalCategoryTitleException extends IllegalArgumentException {

    private static final String MSG = String.format(
            "Category title length must be from %d to %d but was: ",
            Constants.CATEGORY_TITLE_MIN_LENGTH,
            Constants.CATEGORY_TITLE_MAX_LENGTH
    );

    public IllegalCategoryTitleException(String title) {
        super(MSG.concat(String.valueOf(title.length())));
    }

}
