package dgcd.financier.modules.initData;

import dgcd.financier.core.usecase.InitDataGetUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
class InitDataService {

    private final InitDataGetUsecase initDataGetUsecase;
    private final InitDataMapper initDataMapper;

    @Transactional(readOnly = true)
    public InitDataResponseDto getInitData() {
        var response = initDataGetUsecase.execute(null);
        return initDataMapper.fromUsecase(response);
    }

}
