package dgcd.financier.infra.gateway.controller;

import dgcd.financier.infra.gateway.aspects.LogControllerData;
import dgcd.financier.infra.gateway.dto.CommonIdDto;
import dgcd.financier.infra.gateway.dto.CommonResponseDto;
import dgcd.financier.infra.gateway.dto.OperationCreateRequestDto;
import dgcd.financier.infra.gateway.service.OperationsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static dgcd.financier.infra.gateway.WebConstants.OPERATIONS_CANCEL_PATH;
import static dgcd.financier.infra.gateway.WebConstants.OPERATIONS_CREATE_PATH;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(
        consumes = APPLICATION_JSON_VALUE,
        produces = APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
class OperationsController {

    private final OperationsService operationsService;


    @LogControllerData
    @PostMapping(OPERATIONS_CREATE_PATH)
    public CommonResponseDto createOperation(
            @Valid @RequestBody OperationCreateRequestDto dto
    ) {
        var payload = operationsService.createOperation(dto);
        return CommonResponseDto.ok(payload);
    }

    @LogControllerData
    @PostMapping(OPERATIONS_CANCEL_PATH)
    public CommonResponseDto cancelOperation(
            @Valid @RequestBody CommonIdDto dto
    ) {
        var payload = operationsService.cancelOperation(dto);
        return CommonResponseDto.ok(payload);
    }

}
