//package dgcd.financier.infra.gateway.mapper;
//
//import dgcd.financier.core.domain.Category;
//import dgcd.financier.core.usecase.impl.CategoryCreateUsecase;
//import dgcd.financier.infra.gateway.dto.CategoryCreateRequestDto;
//import dgcd.financier.infra.gateway.dto.CategoryResponseDto;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//import org.mapstruct.Mappings;
//
//import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
//
//@Mapper(componentModel = SPRING)
//public interface CategoryMapper {
//
//    @Mappings({
//            @Mapping(target = "id", source = "category.identity"),
//            @Mapping(target = "parentId", source = "category.parent.identity"),
//            @Mapping(target = "parentTitle", source = "category.parent.title"),
//    })
//    CategoryResponseDto fromDomain(Category category);
//
//    @Mappings({
//            @Mapping(target = "parentIdentity", source = "dto.parentId"),
//    })
//    CategoryCreateUsecase.Request toCreateUsecase(CategoryCreateRequestDto dto);
//
//}
