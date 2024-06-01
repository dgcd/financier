package dgcd.financier.port.gateway.controller;

import dgcd.financier.port.gateway.aspects.LogControllerData;
import dgcd.financier.port.gateway.dto.AccountCreateRequestDto;
import dgcd.financier.port.gateway.dto.CommonResponseDto;
import dgcd.financier.port.gateway.service.AccountsService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static dgcd.financier.port.gateway.WebConstants.ACCOUNTS_CREATE_PATH;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(
        consumes = APPLICATION_JSON_VALUE,
        produces = APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
class AccountsController {

    private final AccountsService accountsService;


    @LogControllerData
    @PostMapping(ACCOUNTS_CREATE_PATH)
    @Operation(summary = "Create new account")
    public CommonResponseDto createAccount(@Valid @RequestBody AccountCreateRequestDto dto) {
        var result = accountsService.createAccount(dto);
        return CommonResponseDto.fromEither(result);
    }

//    @LogControllerData
//    @PostMapping(ACCOUNTS_CLOSE_PATH)
//    @Operation(summary = "Close account with zero balance")
//    public CommonResponseDto closeAccount(@Valid @RequestBody CommonIdDto dto) {
//        var payload = accountsService.closeAccount(dto);
//        return CommonResponseDto.ok(payload);
//    }

}
