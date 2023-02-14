package dgcd.financier.infra.repository.mapper;

import dgcd.financier.core.domain.Category;
import dgcd.financier.core.domain.factory.CategoryFactory;
import dgcd.financier.infra.repository.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import static java.util.Objects.isNull;

@Mapper
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);


    @Mappings({
            @Mapping(target = "id", source = "identity"),
            @Mapping(target = "parent.id", source = "parent.identity"),
    })
    CategoryEntity toEntity(Category category);


    default Category fromEntity(CategoryEntity categoryEntity) {
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
