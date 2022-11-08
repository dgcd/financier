package dgcd.financier.infra.gateway.controller;

import dgcd.financier.infra.gateway.WebConstants;
import dgcd.financier.infra.gateway.dto.AccountCreateRequestDto;
import dgcd.financier.infra.gateway.aspects.LogControllerData;
import dgcd.financier.infra.gateway.dto.CommonIdDto;
import dgcd.financier.infra.gateway.dto.CommonResponseDto;
import dgcd.financier.infra.gateway.service.AccountsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @PostMapping(WebConstants.ACCOUNTS_CREATE_PATH)
    public CommonResponseDto createAccount(@Valid @RequestBody AccountCreateRequestDto dto) {
        var payload = accountsService.createAccount(dto);
        return CommonResponseDto.ok(payload);
    }


    @LogControllerData
    @PostMapping(WebConstants.ACCOUNTS_CLOSE_PATH)
    public CommonResponseDto closeAccount(@Valid @RequestBody CommonIdDto dto) {
        var payload = accountsService.closeAccount(dto);
        return CommonResponseDto.ok(payload);
    }

}
