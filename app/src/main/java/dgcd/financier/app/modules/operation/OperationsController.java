package dgcd.financier.app.modules.operation;

import dgcd.financier.app.commons.mvc.aspects.HandleException;
import dgcd.financier.app.commons.mvc.aspects.LogControllerData;
import dgcd.financier.app.commons.mvc.dto.GeneralResponseDto;
import dgcd.financier.app.modules.operation.dto.OperationCreateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static dgcd.financier.app.commons.mvc.config.MvcConstants.OPERATIONS_CREATE_PATH;
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
    @LogControllerData(logResult = true)
    @PostMapping(OPERATIONS_CREATE_PATH)
    public GeneralResponseDto createOperationOrTransfert(@RequestBody OperationCreateRequestDto dto) {
        var payload = operationsService.createOperationOrTransfert(dto);
        return new GeneralResponseDto(payload);
    }

}
