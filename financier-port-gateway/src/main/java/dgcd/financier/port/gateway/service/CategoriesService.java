//package dgcd.financier.infra.gateway.service;
//
//import dgcd.financier.core.usecase.CategoryCreateUsecase;
//import dgcd.financier.infra.gateway.dto.CategoryCreateRequestDto;
//import dgcd.financier.infra.gateway.dto.CategoryResponseDto;
//import dgcd.financier.infra.gateway.mapper.CategoryMapper;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//@Service
//@RequiredArgsConstructor
//public class CategoriesService {
//
//    private final CategoryCreateUsecase categoryCreateUsecase;
//    private final CategoryMapper categoryMapper;
//
//
//    @Transactional
//    public CategoryResponseDto createCategory(CategoryCreateRequestDto dto) {
//        var request = categoryMapper.toCreateUsecase(dto);
//        var response = categoryCreateUsecase.execute(request);
//        return categoryMapper.fromDomain(response.getCategory());
//    }
//
//}
