package dgcd.financier.app.mvc.controller;

import dgcd.financier.app.dto.category.CategoryCreateRequestDto;
import dgcd.financier.app.mvc.aspects.HandleException;
import dgcd.financier.app.mvc.aspects.LogControllerData;
import dgcd.financier.app.mvc.dto.GeneralResponseDto;
import dgcd.financier.app.service.CategoriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static dgcd.financier.app.mvc.config.MvcConstants.CATEGORIES_CREATE_PATH;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(
        consumes = APPLICATION_JSON_VALUE,
        produces = APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
public class CategoriesController {

    private final CategoriesService categoriesService;

    @PostMapping(CATEGORIES_CREATE_PATH)
    @HandleException
    @LogControllerData(logResult = true)
    public GeneralResponseDto createCategory(@RequestBody CategoryCreateRequestDto dto) {
        System.out.println(dto);
        var payload = categoriesService.createCategory(dto);
        return new GeneralResponseDto(payload);
    }

}
