package dgcd.financier.port.gateway.service;

import dgcd.financier.core.api.InitDataGetUsecase;
import dgcd.financier.core.api.error.CommonError;
import dgcd.financier.port.gateway.dto.InitDataResponseDto;
import dgcd.financier.port.gateway.mapper.InitDataDtoMapper;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InitDataService {

    private final InitDataGetUsecase initDataGetUsecase;
    private final InitDataDtoMapper initDataMapper;

    @Transactional(readOnly = true)
    public Either<CommonError, InitDataResponseDto> getInitData() {
        return initDataGetUsecase.execute()
                .map(initDataMapper::fromUsecase);
    }

}
