package dgcd.financier.core.domain.model;

import dgcd.financier.core.domain.Validatable;
import dgcd.financier.core.domain.validation.CategoryValidator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Accessors(chain = true)
public final class Category implements Validatable<Category> {

    private Long id;

    private String title;
    private Long parentId;

    // from db only
    private Category parent;


    public boolean isParent() {
        return parentId == null;
    }


    @Override
    public Category validate() {
        return CategoryValidator.validate(this);
    }

}
