package dgcd.financier.infrastructure.gateway.mapper;

import dgcd.financier.core.domain.Operation;
import dgcd.financier.infrastructure.gateway.dto.OperationResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface OperationMapper {

    @Mappings({
            @Mapping(target = "id", source = "operation.identity"),

            @Mapping(target = "accountId", source = "operation.account.identity"),
            @Mapping(target = "accountTitle", source = "operation.account.title"),
            @Mapping(target = "currency", source = "operation.account.currency"),

            @Mapping(target = "categoryId", source = "operation.subcategory.parent.identity"),
            @Mapping(target = "categoryTitle", source = "operation.subcategory.parent.title"),
            @Mapping(target = "subcategoryId", source = "operation.subcategory.identity"),
            @Mapping(target = "subcategoryTitle", source = "operation.subcategory.title"),
    })
    OperationResponseDto fromDomain(Operation operation);

}
