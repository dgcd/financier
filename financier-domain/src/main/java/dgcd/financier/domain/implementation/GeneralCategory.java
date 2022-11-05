package dgcd.financier.domain.implementation;

import dgcd.financier.domain.Category;
import dgcd.financier.domain.validation.CategoryValidator;
import lombok.Getter;

import static java.util.Objects.isNull;

@Getter
public final class GeneralCategory implements Category {

    private final Long id;
    private final String title;
    private final Category parent;

    public GeneralCategory(
            Long id,
            String title,
            Category parent
    ) {
        this.id = id;
        this.title = title;
        this.parent = parent;
        CategoryValidator.validate(this);
    }

    @Override
    public boolean isParent() {
        return isNull(parent);
    }

}
