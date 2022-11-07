package dgcd.financier.core.usecase.impl;

import dgcd.financier.core.usecase.CategoryCreateUsecase;
import dgcd.financier.core.usecase.port.repository.CategoriesRepository;
import dgcd.financier.core.usecase.validator.CategoryCreateUsecaseRequestValidator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CategoryCreateUsecaseImpl implements CategoryCreateUsecase {

    private final CategoriesRepository categoriesRepository;

    @Override
    public Response execute(Request request) {
        CategoryCreateUsecaseRequestValidator.validate(request);

//        var categoriesWithSameTitle = categoriesDaoService.findAllByTitle(title);
//        for (var cat : categoriesWithSameTitle) {
//            if (isNull(cat.getParent())) {
//                if (isNull(parentId)) {
//                    throw new DuplicatedCategoryTitleException(title, parentId);
//                }
//            } else if (Objects.equals(cat.getParent().getId(), parentId)) {
//                throw new DuplicatedCategoryTitleException(title, parentId);
//            }
//        }

//        var newCategory = dto.makeCategory();

//        if (nonNull(parentId)) {
//            var parentOpt = categoriesDaoService.findById(parentId);
//            if (parentOpt.isEmpty()) {
//                throw new ParentCategoryNotFoundException(parentId);
//            }
//            if (nonNull(parentOpt.get().getParent())) {
//                throw new CategoryCanNotBeParentException(parentId);
//            }
//            newCategory.setParent(parentOpt.get());
//        }

//        var savedCategory = categoriesDaoService.save(newCategory);
//        return CategoryResponseDto.of(savedCategory);

        return new Response(null);
    }

}
