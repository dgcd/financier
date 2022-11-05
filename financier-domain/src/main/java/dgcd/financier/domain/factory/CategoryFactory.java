package dgcd.financier.domain.factory;

import dgcd.financier.domain.Category;
import dgcd.financier.domain.implementation.GeneralCategory;

import java.util.Objects;

public class CategoryFactory {

    public static Category makeNewParent(String title) {
        return new GeneralCategory(
                null,
                title,
                null
        );
    }

    public static Category makeNewChild(String title, Category parent) {
        Objects.requireNonNull(parent, "Parent category can not be null");
        return new GeneralCategory(
                null,
                title,
                parent
        );
    }

}
