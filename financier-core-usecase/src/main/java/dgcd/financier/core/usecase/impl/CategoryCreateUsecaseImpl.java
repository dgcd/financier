package dgcd.financier.core.usecase.impl;

import dgcd.financier.core.domain.Category;
import dgcd.financier.core.domain.factory.CategoryFactory;
import dgcd.financier.core.usecase.CategoryCreateUsecase;
import dgcd.financier.core.usecase.exception.CategoryCanNotBeParentException;
import dgcd.financier.core.usecase.exception.CategoryParentNotFoundException;
import dgcd.financier.core.usecase.exception.CategoryWithTitleAlreadyExistsException;
import dgcd.financier.core.usecase.port.repository.CategoriesRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static dgcd.financier.core.usecase.validator.CategoryCreateUsecaseRequestValidator.validate;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@RequiredArgsConstructor
public class CategoryCreateUsecaseImpl implements CategoryCreateUsecase {

    private final CategoriesRepository categoriesRepository;

    @Override
    public Response execute(Request request) {
        validate(request);

        var categoriesWithSameTitle = categoriesRepository.findAllByTitle(request.getTitle());
        checkForDuplicatedTitle(request.getParentIdentity(), categoriesWithSameTitle);

        Category category;
        if (nonNull(request.getParentIdentity())) {
            var parentOpt = categoriesRepository.findByIdentity(request.getParentIdentity());
            var parent = checkParentCategory(parentOpt, request.getParentIdentity());
            category = CategoryFactory.makeNewSubcategory(request.getTitle(), parent);
        } else {
            category = CategoryFactory.makeNewParent(request.getTitle());
        }

        var saved = categoriesRepository.save(category);
        return new Response(saved);
    }


    private static void checkForDuplicatedTitle(
            Long parentIdentity,
            List<Category> categoriesWithSameTitle
    ) {
        for (var cat : categoriesWithSameTitle) {
            if (isNull(cat.getParent())) {
                if (isNull(parentIdentity)) {
                    throw new CategoryWithTitleAlreadyExistsException(cat.getTitle());
                }
            } else if (Objects.equals(cat.getParent().getIdentity(), parentIdentity)) {
                throw new CategoryWithTitleAlreadyExistsException(cat.getTitle(), cat.getParent().getIdentity());
            }
        }
    }


    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    private static Category checkParentCategory(Optional<Category> parentOpt, Long parentId) {
        if (parentOpt.isEmpty()) {
            throw new CategoryParentNotFoundException(parentId);
        }
        var parent = parentOpt.get();
        if (nonNull(parent.getParent())) {
            throw new CategoryCanNotBeParentException(parentId);
        }
        return parent;
    }

}
