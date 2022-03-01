package dgcd.financier.app.modules.valute;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class ValuteService {

    @Getter
    private volatile BigDecimal rateUsd = BigDecimal.valueOf(101);
    @Getter
    private volatile BigDecimal rateEur = BigDecimal.valueOf(112);

//    private final RestTemplate restTemplate;
//    private final XmlMapper xmlMapper;
//
//    @EventListener(ApplicationStartedEvent.class)
//    public void getValutes() throws JsonProcessingException {
//        String url = "http://www.cbr.ru/scripts/XML_daily.asp?date_req=26/02/2022";
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Host", "www.cbr.ru");
//        headers.set("Accept", "application/xhtml+xml");
//        headers.set("Referer", url);
//        headers.set("Cookie", "_xxxxxxxxxxz");
//        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
//        ResponseEntity<String> response = restTemplate.exchange(url, GET, requestEntity, String.class);
//        log.info("ValCurs: {}", response.getBody());
//        ValCurs value = xmlMapper.readValue(response.getBody(), ValCurs.class);
//        log.info("value: {}", value);
//    }

}
