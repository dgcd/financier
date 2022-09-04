package dgcd.financier.app.modules.operation;

import dgcd.financier.app.infrastructure.aspects.LogControllerData;
import dgcd.financier.app.infrastructure.dto.CommonIdDto;
import dgcd.financier.app.infrastructure.dto.CommonResponseDto;
import dgcd.financier.app.modules.operation.dto.OperationCreateRequestDto;
import dgcd.financier.app.modules.operation.dto.OperationsCancelResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static dgcd.financier.app.infrastructure.web.WebConstants.OPERATIONS_CANCEL_PATH;
import static dgcd.financier.app.infrastructure.web.WebConstants.OPERATIONS_CREATE_PATH;
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
    public CommonResponseDto closeAccount(
            @Valid @RequestBody CommonIdDto dto
    ) {
//        var payload = accountsService.closeAccount(dto);
        return CommonResponseDto.ok(new OperationsCancelResponseDto(
                List.of(dto.id()),
                List.of()
        ));
    }

}
