package dgcd.financier.gateway.service;

import dgcd.financier.core.usecase.InitDataGetUsecase;
import dgcd.financier.gateway.dto.InitDataResponseDto;
import dgcd.financier.gateway.mapper.InitDataMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InitDataService {

    private final InitDataGetUsecase initDataGetUsecase;
    private final InitDataMapper initDataMapper;

    @Transactional(readOnly = true)
    public InitDataResponseDto getInitData() {
        var response = initDataGetUsecase.execute(null);
        return initDataMapper.fromUsecase(response);
    }

}
