package dgcd.financier.app.service;

import dgcd.financier.app.dto.init.InitResponseDto;
import dgcd.financier.app.service.dao.AccountsDaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InitService {

    private final AccountsDaoService accountsDaoService;

    @Transactional(readOnly = true)
    public InitResponseDto getInitData() {
        return new InitResponseDto(
                accountsDaoService.findAll()
        );
    }

}
