package dgcd.financier.infra.gateway.controller;

import dgcd.financier.infra.gateway.aspects.LogControllerData;
import dgcd.financier.infra.gateway.dto.CommonResponseDto;
import dgcd.financier.infra.gateway.service.InitDataService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static dgcd.financier.infra.gateway.WebConstants.INIT_PATH;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
class InitDataController {

    private final InitDataService initDataService;

    @LogControllerData
    @PostMapping(INIT_PATH)
    @Operation(summary = "Get initial data for UI")
    public CommonResponseDto getInitData() {
        var payload = initDataService.getInitData();
        return CommonResponseDto.ok(payload);
    }

}
