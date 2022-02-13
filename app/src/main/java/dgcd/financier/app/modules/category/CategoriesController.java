package dgcd.financier.app.modules.category;

import dgcd.financier.app.commons.mvc.aspects.HandleException;
import dgcd.financier.app.commons.mvc.aspects.LogControllerData;
import dgcd.financier.app.commons.mvc.dto.GeneralResponseDto;
import dgcd.financier.app.modules.category.dto.CategoryCreateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static dgcd.financier.app.commons.mvc.config.MvcConstants.CATEGORIES_CREATE_PATH;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(
        consumes = APPLICATION_JSON_VALUE,
        produces = APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
public class CategoriesController {

    private final CategoriesService categoriesService;

    @HandleException
    @LogControllerData
    @PostMapping(CATEGORIES_CREATE_PATH)
    public GeneralResponseDto createCategory(@RequestBody CategoryCreateRequestDto dto) {
        var payload = categoriesService.createCategory(dto);
        return new GeneralResponseDto(payload);
    }

}
