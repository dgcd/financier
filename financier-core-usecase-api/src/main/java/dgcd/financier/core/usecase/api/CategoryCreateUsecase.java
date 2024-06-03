package dgcd.financier.core.usecase.api;

import dgcd.financier.core.usecase.api.dto.CategoryCreateRequestDto;
import dgcd.financier.core.usecase.api.dto.common.CategoryDto;
import dgcd.financier.core.usecase.api.error.CommonError;
import io.vavr.control.Either;

@FunctionalInterface
public interface CategoryCreateUsecase {

    Either<CommonError, CategoryDto> execute(CategoryCreateRequestDto request);

}
