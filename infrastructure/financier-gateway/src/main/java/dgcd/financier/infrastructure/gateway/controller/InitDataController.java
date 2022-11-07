package dgcd.financier.infrastructure.gateway.controller;

import dgcd.financier.infrastructure.gateway.dto.CommonResponseDto;
import dgcd.financier.infrastructure.gateway.aspects.LogControllerData;
import dgcd.financier.infrastructure.gateway.service.InitDataService;
import dgcd.financier.infrastructure.gateway.WebConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(
        consumes = APPLICATION_JSON_VALUE,
        produces = APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
class InitDataController {

    private final InitDataService initDataService;

    @LogControllerData
    @PostMapping(WebConstants.INIT_PATH)
    public CommonResponseDto getInitData() {
        var payload = initDataService.getInitData();
        return CommonResponseDto.ok(payload);
    }

}
