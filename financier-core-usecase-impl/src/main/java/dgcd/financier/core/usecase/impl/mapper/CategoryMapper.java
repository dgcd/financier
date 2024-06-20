package dgcd.financier.core.usecase.impl.mapper;

import dgcd.financier.core.domain.model.Category;
import dgcd.financier.core.usecase.api.dto.common.CategoryDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);


    @Mapping(target = "parentId", source = "category.parent.id")
    @Mapping(target = "parentTitle", source = "category.parent.title")
    CategoryDto fromDomain(Category category);

    List<CategoryDto> fromDomain(List<Category> categories);

}
