package dgcd.financier.modules.initData;

import dgcd.financier.core.usecase.InitDataGetUsecase;
import dgcd.financier.modules.dto.mapper.AccountMapper;
import dgcd.financier.modules.dto.mapper.CategoryMapper;
import dgcd.financier.modules.dto.mapper.OperationMapper;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(
        componentModel = SPRING,
        uses = {AccountMapper.class, CategoryMapper.class, OperationMapper.class}
)
public interface InitDataGetUsecaseMapper {

    InitDataResponseDto fromUsecase(InitDataGetUsecase.Response response);

}
