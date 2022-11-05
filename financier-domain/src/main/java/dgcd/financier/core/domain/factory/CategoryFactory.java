package dgcd.financier.core.domain.factory;

import dgcd.financier.core.domain.Category;
import dgcd.financier.core.domain.implementation.GeneralCategory;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class CategoryFactory {

    public static Category makeNewParent(String title) {
        return new GeneralCategory(
                null,
                title,
                null
        );
    }

    public static Category makeNewChild(String title, Category parent) {
        requireNonNull(parent, "Parent category can not be null");
        return new GeneralCategory(
                null,
                title,
                parent
        );
    }

}
