package dgcd.financier.core.usecase.impl;

import dgcd.financier.core.domain.model.Category;
import dgcd.financier.core.usecase.api.CategoryCreateUsecase;
import dgcd.financier.core.usecase.api.dto.CategoryCreateRequestDto;
import dgcd.financier.core.usecase.api.dto.common.CategoryDto;
import dgcd.financier.core.usecase.api.error.CommonError;
import dgcd.financier.core.usecase.api.port.repository.CategoriesRepository;
import dgcd.financier.core.usecase.impl.error.UsecaseError;
import dgcd.financier.core.usecase.impl.mapper.CategoryMapper;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

import static dgcd.financier.core.usecase.api.utils.EitherUtils.toRight;
import static dgcd.financier.core.usecase.impl.error.Errors.CATEGORY_ALREADY_EXISTS;
import static dgcd.financier.core.usecase.impl.error.Errors.CATEGORY_CAN_NOT_BE_PARENT;
import static dgcd.financier.core.usecase.impl.error.Errors.CATEGORY_PARENT_NOT_FOUND;
import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;

@RequiredArgsConstructor
public class CategoryCreateUsecaseImpl implements CategoryCreateUsecase {

    private final CategoriesRepository categoriesRepository;

    @Override
    public Either<CommonError, CategoryDto> execute(CategoryCreateRequestDto request) {
        return toRight(request)
                .flatMap(this::checkNotExists)
                .flatMap(this::findAndCheckParentIfNeeded)
                .flatMap(this::createCategory)
                .map(categoriesRepository::save)
                .map(CategoryMapper.INSTANCE::fromDomain);
    }


    private Either<CommonError, CategoryCreateRequestDto> checkNotExists(CategoryCreateRequestDto request) {
        var categories = categoriesRepository.findAllByTitle(request.title());

        for (var cat : categories) {
            if (cat.getParent() == null) {
                if (request.createParent()) {
                    return left(CATEGORY_ALREADY_EXISTS);
                }
            } else if (Objects.equals(cat.getParent().getId(), request.parentId())) {
                return left(CATEGORY_ALREADY_EXISTS);
            }
        }

        return right(request);
    }


    private Either<CommonError, CategoryCreateRequestDto> findAndCheckParentIfNeeded(CategoryCreateRequestDto request) {
        if (request.createParent()) {
            return right(request);
        }

        var parentOpt = categoriesRepository.findById(request.parentId());
        if (parentOpt.isEmpty()) {
            return left(CATEGORY_PARENT_NOT_FOUND);
        }

        var parent = parentOpt.get();
        if (!parent.isParent()) {
            return left(CATEGORY_CAN_NOT_BE_PARENT);
        }

        return right(request);
    }


    private Either<CommonError, Category> createCategory(CategoryCreateRequestDto request) {
        try {
            var category = new Category()
                    .setTitle(request.title())
                    .setParentId(request.parentId())
                    .validate();
            return right(category);
        } catch (IllegalArgumentException ex) {
            return left(new UsecaseError(ex.getMessage()));
        }
    }

}
