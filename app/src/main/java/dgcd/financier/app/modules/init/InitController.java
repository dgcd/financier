package dgcd.financier.app.modules.init;

import dgcd.financier.app.commons.mvc.aspects.LogControllerData;
import dgcd.financier.app.commons.mvc.dto.GeneralResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static dgcd.financier.app.commons.mvc.config.MvcConstants.INIT_PATH;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(
        consumes = APPLICATION_JSON_VALUE,
        produces = APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
public class InitController {

    private final InitService initService;

    @LogControllerData
    @PostMapping(INIT_PATH)
    public GeneralResponseDto getInitData() {
        var payload = initService.getInitData();
        return new GeneralResponseDto(payload);
    }

}
