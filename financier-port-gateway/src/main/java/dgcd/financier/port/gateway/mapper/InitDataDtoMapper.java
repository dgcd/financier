package dgcd.financier.port.gateway.mapper;

import dgcd.financier.core.usecase.api.dto.InitDataGetResponseDto;
import dgcd.financier.port.gateway.dto.InitDataResponseDto;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface InitDataDtoMapper {

    InitDataResponseDto fromUsecase(InitDataGetResponseDto response);

}
