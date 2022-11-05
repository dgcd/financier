package dgcd.financier.domain.validation;

import dgcd.financier.domain.Category;
import dgcd.financier.domain.exception.IllegalCategoryTitleException;

import static dgcd.financier.domain.Constants.CATEGORY_TITLE_MAX_LENGTH;
import static dgcd.financier.domain.Constants.CATEGORY_TITLE_MIN_LENGTH;
import static dgcd.financier.domain.validation.ValidationUtils.checkStringBoundaries;
import static java.util.Objects.requireNonNull;

public class CategoryValidator {

    public static void validate(Category category) {
        var title = category.getTitle();
        requireNonNull(title, "Title can not be null");
        checkStringBoundaries(
                title,
                CATEGORY_TITLE_MIN_LENGTH,
                CATEGORY_TITLE_MAX_LENGTH,
                IllegalCategoryTitleException::new
        );
    }

}
