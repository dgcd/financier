package dgcd.financier.infrastructure.gateway.controller;

import dgcd.financier.infrastructure.gateway.dto.CategoryCreateRequestDto;
import dgcd.financier.infrastructure.gateway.dto.CommonResponseDto;
import dgcd.financier.infrastructure.gateway.aspects.LogControllerData;
import dgcd.financier.infrastructure.gateway.service.CategoriesService;
import dgcd.financier.infrastructure.gateway.WebConstants;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(
        consumes = APPLICATION_JSON_VALUE,
        produces = APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
class CategoriesController {

    private final CategoriesService categoriesService;


    @LogControllerData
    @PostMapping(WebConstants.CATEGORIES_CREATE_PATH)
    public CommonResponseDto createCategory(
            @Valid @RequestBody CategoryCreateRequestDto dto
    ) {
        var payload = categoriesService.createCategory(dto);
        return CommonResponseDto.ok(payload);
    }

}
