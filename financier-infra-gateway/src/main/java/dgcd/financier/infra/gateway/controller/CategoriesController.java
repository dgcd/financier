package dgcd.financier.infra.gateway.controller;

import dgcd.financier.infra.gateway.aspects.LogControllerData;
import dgcd.financier.infra.gateway.dto.CategoryCreateRequestDto;
import dgcd.financier.infra.gateway.dto.CommonResponseDto;
import dgcd.financier.infra.gateway.service.CategoriesService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static dgcd.financier.infra.gateway.WebConstants.CATEGORIES_CREATE_PATH;
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
    @PostMapping(CATEGORIES_CREATE_PATH)
    @Operation(summary = "Create new category")
    public CommonResponseDto createCategory(@Valid @RequestBody CategoryCreateRequestDto dto) {
        var payload = categoriesService.createCategory(dto);
        return CommonResponseDto.ok(payload);
    }

}
