package dgcd.financier.port.gateway.mapper;

import dgcd.financier.core.usecase.api.dto.CategoryCreateRequestDto;
import dgcd.financier.core.usecase.api.dto.common.CategoryDto;
import dgcd.financier.port.gateway.dto.CategoryResponseDto;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface CategoryDtoMapper {

    CategoryResponseDto fromUsecase(CategoryDto category);

    CategoryCreateRequestDto toCreateUsecase(dgcd.financier.port.gateway.dto.CategoryCreateRequestDto dto);

}
