package dgcd.financier.port.gateway.controller;

import dgcd.financier.port.gateway.aspects.LogControllerData;
import dgcd.financier.port.gateway.dto.CategoryCreateRequestDto;
import dgcd.financier.port.gateway.dto.CommonResponseDto;
import dgcd.financier.port.gateway.service.CategoriesService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static dgcd.financier.port.gateway.WebConstants.CATEGORIES_CREATE_PATH;
import static dgcd.financier.port.gateway.controller.ResponseUtils.fromEither;
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
    public ResponseEntity<CommonResponseDto> createCategory(@Valid @RequestBody CategoryCreateRequestDto dto) {
        var result = categoriesService.createCategory(dto);
        return fromEither(result);
    }

}
