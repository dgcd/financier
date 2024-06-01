package dgcd.financier.core.usecase.impl.mapper;

import dgcd.financier.core.api.dto.common.OperationDto;
import dgcd.financier.core.domain.model.Operation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface OperationMapper {

    OperationMapper INSTANCE = Mappers.getMapper(OperationMapper.class);


    @Mapping(target = "accountTitle", source = "operation.account.title")
    @Mapping(target = "currency", source = "operation.account.currency")
    @Mapping(target = "subcategoryTitle", source = "operation.subcategory.title")
    @Mapping(target = "categoryId", source = "operation.subcategory.parentId")
    @Mapping(target = "categoryTitle", source = "operation.subcategory.parent.title")
    OperationDto fromDomain(Operation operation);

    List<OperationDto> fromDomain(List<Operation> operations);

}
