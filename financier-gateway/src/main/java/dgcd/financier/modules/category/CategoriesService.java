package dgcd.financier.modules.category;

import dgcd.financier.core.usecase.CategoryCreateUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
class CategoriesService {

    private final CategoryCreateUsecase categoryCreateUsecase;
    private final CategoryMapper categoryMapper;


    @Transactional
    public CategoryResponseDto createCategory(CategoryCreateRequestDto dto) {
        var request = categoryMapper.toCreateUsecase(dto);
        var response = categoryCreateUsecase.execute(request);
        return categoryMapper.fromDomain(response.getCategory());
    }

}
