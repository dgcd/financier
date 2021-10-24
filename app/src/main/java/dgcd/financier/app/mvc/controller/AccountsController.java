package dgcd.financier.app.mvc.controller;

import dgcd.financier.app.mvc.aspects.HandleException;
import dgcd.financier.app.mvc.aspects.LogControllerData;
import dgcd.financier.app.mvc.dto.GeneralResponseDto;
import dgcd.financier.app.service.AccountsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static dgcd.financier.app.mvc.config.MvcConstants.ACCOUNTS_PATH;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(
        value = ACCOUNTS_PATH,
        consumes = APPLICATION_JSON_VALUE,
        produces = APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
public class AccountsController {

    private final AccountsService accountsService;

    @PostMapping
    @HandleException
    @LogControllerData(logResult = true)
    public GeneralResponseDto getAccounts() {
        var payload = accountsService.getAccounts();
        return new GeneralResponseDto(payload);
    }

}
