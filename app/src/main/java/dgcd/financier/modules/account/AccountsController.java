package dgcd.financier.modules.account;

import dgcd.financier.infrastructure.dto.CommonResponseDto;
import dgcd.financier.infrastructure.web.WebConstants;
import dgcd.financier.usecase.AccountCreateCase;
import dgcd.financier.usecase.dto.AccountCreateRequestDto;
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

    private final AccountCreateCase createAccount;


    //    @LogControllerData
    @PostMapping(WebConstants.ACCOUNTS_CREATE_PATH)
    public CommonResponseDto createAccount(
            @Valid @RequestBody AccountCreateRequestDto dto
    ) {
        var payload = createAccount.createAccount(dto);
        return CommonResponseDto.ok(payload);
    }

//    @LogControllerData
//    @PostMapping(ACCOUNTS_CLOSE_PATH)
//    public CommonResponseDto closeAccount(
//            @Valid @RequestBody CommonIdDto dto
//    ) {
//        var payload = accountsService.closeAccount(dto);
//        return CommonResponseDto.ok(payload);
//    }

}
