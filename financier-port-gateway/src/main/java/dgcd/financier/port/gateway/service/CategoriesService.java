package dgcd.financier.port.gateway.service;

import dgcd.financier.core.usecase.api.CategoryCreateUsecase;
import dgcd.financier.core.usecase.api.error.CommonError;
import dgcd.financier.port.gateway.dto.CategoryCreateRequestDto;
import dgcd.financier.port.gateway.dto.CategoryResponseDto;
import dgcd.financier.port.gateway.mapper.CategoryDtoMapper;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static dgcd.financier.core.usecase.api.utils.EitherUtils.toRight;

@Service
@RequiredArgsConstructor
public class CategoriesService {

    private final CategoryCreateUsecase categoryCreateUsecase;
    private final CategoryDtoMapper categoryMapper;


    @Transactional
    public Either<CommonError, CategoryResponseDto> createCategory(CategoryCreateRequestDto dto) {
        return toRight(dto)
                .map(categoryMapper::toCreateUsecase)
                .flatMap(categoryCreateUsecase::execute)
                .map(categoryMapper::fromUsecase);
    }

}
