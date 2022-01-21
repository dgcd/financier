package dgcd.financier.app.service;

import dgcd.financier.app.domain.model.Category;
import dgcd.financier.app.dto.category.CategoryCreateRequestDto;
import dgcd.financier.app.dto.category.CategoryResponseDto;
import dgcd.financier.app.service.dao.CategoriesDaoService;
import dgcd.financier.app.service.exception.CategoryCanNotBeParentException;
import dgcd.financier.app.service.exception.ParentCategoryNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class CategoriesService {

    private final CategoriesDaoService categoriesDaoService;


    @Transactional
    public CategoryResponseDto createCategory(CategoryCreateRequestDto dto) {
        Category parentCategory = null;
        if (nonNull(dto.parentId())) {
            var parentOpt = categoriesDaoService.findById(dto.parentId());
            if (parentOpt.isEmpty()) {
                throw new ParentCategoryNotFoundException(dto.parentId());
            }
            if (nonNull(parentOpt.get().getParent())) {
                throw new CategoryCanNotBeParentException(dto.parentId());
            }
            parentCategory = parentOpt.get();
        }

        var newCat = new Category(
                null,
                dto.title(),
                parentCategory
        );

        var savedCategory = categoriesDaoService.save(newCat);
        return CategoryResponseDto.of(savedCategory);
    }

}
