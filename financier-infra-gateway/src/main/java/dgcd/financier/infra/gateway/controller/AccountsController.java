package dgcd.financier.infra.gateway.controller;

import dgcd.financier.infra.gateway.aspects.LogControllerData;
import dgcd.financier.infra.gateway.dto.AccountCreateRequestDto;
import dgcd.financier.infra.gateway.dto.CommonIdDto;
import dgcd.financier.infra.gateway.dto.CommonResponseDto;
import dgcd.financier.infra.gateway.service.AccountsService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static dgcd.financier.infra.gateway.WebConstants.ACCOUNTS_CLOSE_PATH;
import static dgcd.financier.infra.gateway.WebConstants.ACCOUNTS_CREATE_PATH;
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
        var payload = accountsService.createAccount(dto);
        return CommonResponseDto.ok(payload);
    }


    @LogControllerData
    @PostMapping(ACCOUNTS_CLOSE_PATH)
    @Operation(summary = "Close account with zero balance")
    public CommonResponseDto closeAccount(@Valid @RequestBody CommonIdDto dto) {
        var payload = accountsService.closeAccount(dto);
        return CommonResponseDto.ok(payload);
    }

}
