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
        checkDuplicatedTitles(dto);
        var newCategory = dto.makeCategory();
        setParentIfPresent(dto, newCategory);
        var savedCategory = categoriesDaoService.save(newCategory);
        return CategoryResponseDto.of(savedCategory);
    }


    private void checkDuplicatedTitles(CategoryCreateRequestDto dto) {
        var categoriesWithSameTitle = categoriesDaoService.findAllByTitle(dto.title());
        for (var cat : categoriesWithSameTitle) {
            if (isNull(cat.getParent())) {
                if (isNull(dto.parentId())) {
                    throw new DuplicatedCategoryTitleException(dto.title(), dto.parentId());
                }
            } else if (Objects.equals(cat.getParent().getId(), dto.parentId())) {
                throw new DuplicatedCategoryTitleException(dto.title(), dto.parentId());
            }
        }
    }


    private void setParentIfPresent(CategoryCreateRequestDto dto, Category newCategory) {
        if (nonNull(dto.parentId())) {
            var parentOpt = categoriesDaoService.findById(dto.parentId());
            if (parentOpt.isEmpty()) {
                throw new ParentCategoryNotFoundException(dto.parentId());
            }
            if (nonNull(parentOpt.get().getParent())) {
                throw new CategoryCanNotBeParentException(dto.parentId());
            }
            newCategory.setParent(parentOpt.get());
        }
    }

}
