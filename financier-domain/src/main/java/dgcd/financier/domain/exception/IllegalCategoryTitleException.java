package dgcd.financier.domain.exception;

import static dgcd.financier.domain.Constants.CATEGORY_TITLE_MAX_LENGTH;
import static dgcd.financier.domain.Constants.CATEGORY_TITLE_MIN_LENGTH;

public class IllegalCategoryTitleException extends IllegalArgumentException {

    private static final String MSG = String.format(
            "Category title length must be from %d to %d but was: ",
            CATEGORY_TITLE_MIN_LENGTH,
            CATEGORY_TITLE_MAX_LENGTH
    );

    public IllegalCategoryTitleException(String title) {
        super(MSG.concat(String.valueOf(title.length())));
    }

}
