package dgcd.financier.modules.dto.mapper;

import dgcd.financier.core.domain.Category;
import dgcd.financier.modules.dto.CategoryResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface CategoryMapper {

    @Mappings({
            @Mapping(target = "id", source = "category.identity"),
            @Mapping(target = "parentId", source = "category.parent.identity"),
            @Mapping(target = "parentTitle", source = "category.parent.title"),
    })
    CategoryResponseDto fromDomain(Category category);

}
