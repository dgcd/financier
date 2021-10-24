package dgcd.financier.app.mvc.controller;

import dgcd.financier.app.domain.model.Account;
import dgcd.financier.app.service.AccountsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static dgcd.financier.app.mvc.config.MvcConstants.ACCOUNTS_PATH;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
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
    public List<Account> getAccounts() {
        var resp = accountsService.getAccounts();
        log.info(resp.toString());
        return resp;
    }

}
