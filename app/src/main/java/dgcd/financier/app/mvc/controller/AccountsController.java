package dgcd.financier.app.mvc.controller;

import dgcd.financier.app.dto.account.AccountCreateRequestDto;
import dgcd.financier.app.mvc.aspects.HandleException;
import dgcd.financier.app.mvc.aspects.LogControllerData;
import dgcd.financier.app.mvc.dto.GeneralResponseDto;
import dgcd.financier.app.service.AccountsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static dgcd.financier.app.mvc.config.MvcConstants.ACCOUNT_CREATE_PATH;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(
        consumes = APPLICATION_JSON_VALUE,
        produces = APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
public class AccountsController {

    private final AccountsService accountsService;

    @PostMapping(ACCOUNT_CREATE_PATH)
    @HandleException
    @LogControllerData(logResult = true)
    public GeneralResponseDto createAccount(@RequestBody AccountCreateRequestDto dto) {
        var payload = accountsService.createAccount(dto);
        return new GeneralResponseDto(payload);
    }

}
