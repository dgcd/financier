package dgcd.financier.app.modules.operation;

import dgcd.financier.app.commons.mvc.aspects.LogControllerData;
import dgcd.financier.app.commons.mvc.dto.GeneralResponseDto;
import dgcd.financier.app.modules.operation.dto.OperationCreateRequestDto;
import jakarta.validation.Valid;
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

    @LogControllerData(logResult = true)
    @PostMapping(OPERATIONS_CREATE_PATH)
    public GeneralResponseDto createOperation(@Valid @RequestBody OperationCreateRequestDto dto) {
        var payload = operationsService.createOperation(dto);
        return new GeneralResponseDto(payload);
    }

}
