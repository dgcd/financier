package dgcd.financier.core.domain.validation;

import dgcd.financier.core.domain.Category;
import dgcd.financier.core.domain.exception.IllegalCategoryTitleException;

import static dgcd.financier.core.domain.Constants.CATEGORY_TITLE_MAX_LENGTH;
import static dgcd.financier.core.domain.Constants.CATEGORY_TITLE_MIN_LENGTH;
import static dgcd.financier.core.domain.validation.ValidationUtils.checkIdentity;
import static dgcd.financier.core.domain.validation.ValidationUtils.checkStringBoundaries;
import static java.util.Objects.requireNonNull;

public class CategoryValidator {

    public static void validate(Category category) {
        checkIdentity(category.getIdentity());

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
