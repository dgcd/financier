package dgcd.financier.infra.repository.mapper;

import dgcd.financier.core.domain.Category;
import dgcd.financier.core.domain.factory.CategoryFactory;
import dgcd.financier.infra.repository.entity.CategoryEntity;

import static java.util.Objects.isNull;

public class CategoryMapper {

    public static CategoryEntity toEntity(Category category) {
        if (isNull(category)) {
            return null;
        }
        return new CategoryEntity(
                category.getIdentity(),
                category.getTitle(),
                toEntity(category.getParent())
        );
    }

    public static Category fromEntity(CategoryEntity categoryEntity) {
        if (isNull(categoryEntity)) {
            return null;
        }
        if (isNull(categoryEntity.getParent())) {
            return CategoryFactory.makeExistingParent(
                    categoryEntity.getId(),
                    categoryEntity.getTitle(),
                    null
            );
        } else {
            return CategoryFactory.makeExistingSubcategory(
                    categoryEntity.getId(),
                    categoryEntity.getTitle(),
                    CategoryFactory.makeExistingParent(
                            categoryEntity.getParent().getId(),
                            categoryEntity.getParent().getTitle(),
                            null
                    )
            );
        }

    }

}
