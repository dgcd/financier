package dgcd.financier.infrastructure.gateway.controller;

import dgcd.financier.infrastructure.gateway.dto.AccountCreateRequestDto;
import dgcd.financier.infrastructure.gateway.aspects.LogControllerData;
import dgcd.financier.infrastructure.gateway.dto.CommonIdDto;
import dgcd.financier.infrastructure.gateway.dto.CommonResponseDto;
import dgcd.financier.infrastructure.gateway.service.AccountsService;
import dgcd.financier.infrastructure.gateway.WebConstants;
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
