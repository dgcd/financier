package dgcd.financier.gateway.controller;

import dgcd.financier.gateway.dto.CommonResponseDto;
import dgcd.financier.gateway.aspects.LogControllerData;
import dgcd.financier.gateway.service.InitDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static dgcd.financier.gateway.WebConstants.INIT_PATH;
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
    @PostMapping(INIT_PATH)
    public CommonResponseDto getInitData() {
        var payload = initDataService.getInitData();
        return CommonResponseDto.ok(payload);
    }

}
