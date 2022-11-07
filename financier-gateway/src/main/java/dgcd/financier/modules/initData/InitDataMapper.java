package dgcd.financier.modules.initData;

import dgcd.financier.core.usecase.InitDataGetUsecase;
import dgcd.financier.modules.account.AccountMapper;
import dgcd.financier.modules.category.CategoryMapper;
import dgcd.financier.modules.operation.OperationMapper;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(
        componentModel = SPRING,
        uses = {AccountMapper.class, CategoryMapper.class, OperationMapper.class}
)
interface InitDataMapper {

    InitDataResponseDto fromUsecase(InitDataGetUsecase.Response response);

}
