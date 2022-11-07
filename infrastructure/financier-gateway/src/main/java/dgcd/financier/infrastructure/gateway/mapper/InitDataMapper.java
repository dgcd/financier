package dgcd.financier.infrastructure.gateway.mapper;

import dgcd.financier.core.usecase.InitDataGetUsecase;
import dgcd.financier.infrastructure.gateway.dto.InitDataResponseDto;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(
        componentModel = SPRING,
        uses = {AccountMapper.class, CategoryMapper.class, OperationMapper.class}
)
public interface InitDataMapper {

    InitDataResponseDto fromUsecase(InitDataGetUsecase.Response response);

}
