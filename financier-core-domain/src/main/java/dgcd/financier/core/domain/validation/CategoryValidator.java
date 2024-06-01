package dgcd.financier.core.domain.validation;

import dgcd.financier.core.domain.model.Category;

import static dgcd.financier.core.domain.Constants.CATEGORY_TITLE_MAX_LENGTH;
import static dgcd.financier.core.domain.Constants.CATEGORY_TITLE_MIN_LENGTH;
import static dgcd.financier.core.domain.validation.ValidationUtils.checkId;
import static dgcd.financier.core.domain.validation.ValidationUtils.checkLength;
import static dgcd.financier.core.domain.validation.ValidationUtils.checkNonNull;

public class CategoryValidator {

    public static Category validate(Category category) {
        checkId(category.getId(), "id");

        checkNonNull(category.getTitle(), "title");
        checkLength(category.getTitle(), CATEGORY_TITLE_MIN_LENGTH, CATEGORY_TITLE_MAX_LENGTH, "title");

        checkId(category.getParentId(), "parentId");

        return category;
    }

}
