package dgcd.financier.app.mvc.controller;

import dgcd.financier.app.domain.dict.Currency;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

import static dgcd.financier.app.mvc.config.MvcConstants.TEST_PATH;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping(produces = APPLICATION_JSON_VALUE)
public class TestController {

    public record TestResponseDto(
            List<String> currencies
    ) {
    }


    @GetMapping(TEST_PATH)
    public TestResponseDto getCurrencies() {
        var list = Arrays.stream(Currency.values())
                .map(Currency::name)
                .toList();
        log.info(list.toString());
        return new TestResponseDto(list);
    }

}
