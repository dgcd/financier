package dgcd.financier.app.modules.category;

import dgcd.financier.app.infrastructure.aspects.LogControllerData;
import dgcd.financier.app.infrastructure.dto.CommonResponseDto;
import dgcd.financier.app.modules.category.dto.CategoryCreateRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static dgcd.financier.app.infrastructure.web.WebConstants.CATEGORIES_CREATE_PATH;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(
        consumes = APPLICATION_JSON_VALUE,
        produces = APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
public class CategoriesController {

    private final CategoriesService categoriesService;


    @LogControllerData
    @PostMapping(CATEGORIES_CREATE_PATH)
    public CommonResponseDto createCategory(
            @Valid @RequestBody CategoryCreateRequestDto dto
    ) {
        var payload = categoriesService.createCategory(dto);
        return CommonResponseDto.ok(payload);
    }

}
