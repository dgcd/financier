package dgcd.financier.modules.initData;

import dgcd.financier.core.usecase.InitDataGetUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
class InitDataService {

    private final InitDataGetUsecase initDataGetUsecase;

    @Transactional(readOnly = true)
    public InitDataResponseDto getInitData() {
        var response = initDataGetUsecase.execute(new InitDataGetUsecase.Request());
        return new InitDataResponseDto(
                List.of(),
                List.of(),
                List.of(),
                response.getRates(),
                Map.of()
        );
    }

}
