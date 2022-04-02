package dgcd.financier.app.modules.category;

import dgcd.financier.app.modules.category.dto.CategoryCreateRequestDto;
import dgcd.financier.app.modules.category.dto.CategoryResponseDto;
import dgcd.financier.app.modules.category.exception.CategoryCanNotBeParentException;
import dgcd.financier.app.modules.category.exception.DuplicatedCategoryTitleException;
import dgcd.financier.app.modules.category.exception.ParentCategoryNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class CategoriesService {

    private final CategoriesDaoService categoriesDaoService;


    @Transactional
    public CategoryResponseDto createCategory(CategoryCreateRequestDto dto) {
        checkDuplicatedTitles(dto.title(), dto.parentId());
        var newCategory = dto.makeCategory();
        setParentIfPresent(newCategory, dto.parentId());
        var savedCategory = categoriesDaoService.save(newCategory);
        return CategoryResponseDto.of(savedCategory);
    }


    private void checkDuplicatedTitles(
            String title,
            Long parentId
    ) {
        var categoriesWithSameTitle = categoriesDaoService.findAllByTitle(title);
        for (var cat : categoriesWithSameTitle) {
            if (isNull(cat.getParent())) {
                if (isNull(parentId)) {
                    throw new DuplicatedCategoryTitleException(title, parentId);
                }
            } else if (Objects.equals(cat.getParent().getId(), parentId)) {
                throw new DuplicatedCategoryTitleException(title, parentId);
            }
        }
    }


    private void setParentIfPresent(
            Category newCategory,
            Long parentId
    ) {
        if (nonNull(parentId)) {
            var parentOpt = categoriesDaoService.findById(parentId);
            if (parentOpt.isEmpty()) {
                throw new ParentCategoryNotFoundException(parentId);
            }
            if (nonNull(parentOpt.get().getParent())) {
                throw new CategoryCanNotBeParentException(parentId);
            }
            newCategory.setParent(parentOpt.get());
        }
    }

}
