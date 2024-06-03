package dgcd.financier.port.gateway.controller;

import dgcd.financier.port.gateway.aspects.LogControllerData;
import dgcd.financier.port.gateway.dto.CommonResponseDto;
import dgcd.financier.port.gateway.dto.OperationCreateRequestDto;
import dgcd.financier.port.gateway.service.OperationsService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static dgcd.financier.port.gateway.WebConstants.OPERATIONS_CREATE_PATH;
import static dgcd.financier.port.gateway.controller.ResponseUtils.fromEither;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(
        consumes = APPLICATION_JSON_VALUE,
        produces = APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
class OperationsController {

    private final OperationsService operationsService;


    @LogControllerData(logResult = true)
    @PostMapping(OPERATIONS_CREATE_PATH)
    @Operation(summary = "Create new operation")
    public ResponseEntity<CommonResponseDto> createOperation(@Valid @RequestBody OperationCreateRequestDto dto) {
        var result = operationsService.createOperation(dto);
        return fromEither(result);
    }

//    @LogControllerData
//    @PostMapping(OPERATIONS_CANCEL_PATH)
//    @Operation(summary = "Cancel operation")
//    public CommonResponseDto cancelOperation(@Valid @RequestBody CommonIdDto dto) {
//        var payload = operationsService.cancelOperation(dto);
//        return CommonResponseDto.ok(payload);
//    }

}
