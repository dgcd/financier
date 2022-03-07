package dgcd.financier.app.modules.account;

import dgcd.financier.app.commons.mvc.aspects.LogControllerData;
import dgcd.financier.app.commons.mvc.dto.GeneralResponseDto;
import dgcd.financier.app.modules.account.dto.AccountCreateRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static dgcd.financier.app.commons.mvc.config.MvcConstants.ACCOUNTS_CREATE_PATH;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(
        consumes = APPLICATION_JSON_VALUE,
        produces = APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
public class AccountsController {

    private final AccountsService accountsService;

    @LogControllerData
    @PostMapping(ACCOUNTS_CREATE_PATH)
    public GeneralResponseDto createAccount(@Valid @RequestBody AccountCreateRequestDto dto) {
        var payload = accountsService.createAccount(dto);
        return new GeneralResponseDto(payload);
    }

}
