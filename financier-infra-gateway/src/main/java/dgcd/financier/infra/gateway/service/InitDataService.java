package dgcd.financier.infra.gateway.service;

import dgcd.financier.core.usecase.InitDataGetUsecase;
import dgcd.financier.infra.gateway.dto.InitDataResponseDto;
import dgcd.financier.infra.gateway.mapper.InitDataMapper;
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
        var response = initDataGetUsecase.execute();
        return initDataMapper.fromUsecase(response);
    }

}
