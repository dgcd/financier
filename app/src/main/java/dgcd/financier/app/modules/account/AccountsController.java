package dgcd.financier.app.modules.account;

import dgcd.financier.app.infrastructure.aspects.LogControllerData;
import dgcd.financier.app.infrastructure.dto.CommonIdDto;
import dgcd.financier.app.infrastructure.dto.CommonResponseDto;
import dgcd.financier.app.modules.account.dto.AccountCreateRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static dgcd.financier.app.infrastructure.web.WebConstants.ACCOUNTS_CLOSE_PATH;
import static dgcd.financier.app.infrastructure.web.WebConstants.ACCOUNTS_CREATE_PATH;
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
    public CommonResponseDto createAccount(
            @Valid @RequestBody AccountCreateRequestDto dto
    ) {
        var payload = accountsService.createAccount(dto);
        return CommonResponseDto.ok(payload);
    }


    @LogControllerData
    @PostMapping(ACCOUNTS_CLOSE_PATH)
    public CommonResponseDto closeAccount(
            @Valid @RequestBody CommonIdDto dto
    ) {
        var payload = accountsService.closeAccount(dto);
        return CommonResponseDto.ok(payload);
    }

}
