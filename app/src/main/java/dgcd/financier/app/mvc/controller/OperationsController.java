package dgcd.financier.app.mvc.controller;

import dgcd.financier.app.dto.operation.OperationCreateRequestDto;
import dgcd.financier.app.mvc.aspects.HandleException;
import dgcd.financier.app.mvc.aspects.LogControllerData;
import dgcd.financier.app.mvc.dto.GeneralResponseDto;
import dgcd.financier.app.service.OperationsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static dgcd.financier.app.mvc.config.MvcConstants.OPERATIONS_CREATE_PATH;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(
        consumes = APPLICATION_JSON_VALUE,
        produces = APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
public class OperationsController {

    private final OperationsService operationsService;

    @HandleException
    @LogControllerData
    @PostMapping(OPERATIONS_CREATE_PATH)
    public GeneralResponseDto createOperation(@RequestBody OperationCreateRequestDto dto) {
        var payload = operationsService.createOperation(dto);
        return new GeneralResponseDto(payload);
    }

}
