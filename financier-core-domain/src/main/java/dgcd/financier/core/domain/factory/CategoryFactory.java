package dgcd.financier.core.domain.factory;

import dgcd.financier.core.domain.Category;
import dgcd.financier.core.domain.implementation.GeneralCategory;

import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;

public class CategoryFactory {

    public static Category makeNewParent(String title) {
        return new GeneralCategory(
                null,
                title,
                null
        );
    }


    public static Category makeExistingParent(Long id, String title, Category mustBeNull) {
        if (nonNull(mustBeNull)) {
            throw new IllegalArgumentException("Parent category can not have parent");
        }
        return new GeneralCategory(
                id,
                title,
                null
        );
    }


    public static Category makeNewSubcategory(String title, Category parent) {
        requireNonNull(parent, "Parent category can not be null");
        return new GeneralCategory(
                null,
                title,
                parent
        );
    }


    public static Category makeExistingSubcategory(Long id, String title, Category parent) {
        requireNonNull(parent, "Parent category can not be null");
        return new GeneralCategory(
                id,
                title,
                parent
        );
    }

}
