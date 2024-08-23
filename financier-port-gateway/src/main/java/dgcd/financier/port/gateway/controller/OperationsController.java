package dgcd.financier.port.gateway.controller;

import dgcd.financier.port.gateway.aspects.LogControllerData;
import dgcd.financier.port.gateway.dto.CommonIdDto;
import dgcd.financier.port.gateway.dto.CommonResponseDto;
import dgcd.financier.port.gateway.dto.OperationCreateRequestDto;
import dgcd.financier.port.gateway.dto.OperationEditRequestDto;
import dgcd.financier.port.gateway.service.OperationsService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static dgcd.financier.port.gateway.WebConstants.OPERATIONS_CANCEL_PATH;
import static dgcd.financier.port.gateway.WebConstants.OPERATIONS_CREATE_PATH;
import static dgcd.financier.port.gateway.WebConstants.OPERATIONS_EDIT_PATH;
import static dgcd.financier.port.gateway.controller.ResponseUtils.fromEither;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
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

    @LogControllerData(logResult = true)
    @PostMapping(OPERATIONS_EDIT_PATH)
    @Operation(summary = "Edit existing operation")
    public ResponseEntity<CommonResponseDto> editOperation(@Valid @RequestBody OperationEditRequestDto dto) {
        var result = operationsService.editOperation(dto);
        return fromEither(result);
    }

    @LogControllerData
    @PostMapping(OPERATIONS_CANCEL_PATH)
    @Operation(summary = "Cancel operation")
    public ResponseEntity<CommonResponseDto> cancelOperation(@Valid @RequestBody CommonIdDto dto) {
        var result = operationsService.cancelOperation(dto);
        return fromEither(result);
    }

}
