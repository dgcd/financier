package dgcd.financier.port.gateway.mapper;

import dgcd.financier.core.usecase.api.OperationCreateUsecase;
import dgcd.financier.core.usecase.api.dto.common.OperationDto;
import dgcd.financier.port.gateway.dto.OperationCreateRequestDto;
import dgcd.financier.port.gateway.dto.OperationResponseDto;
import dgcd.financier.port.gateway.dto.OperationsCreateResponseDto;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(
        componentModel = SPRING
//        ,
//        uses = {AccountMapper.class, CategoryMapper.class}
)
public interface OperationDtoMapper {

    //    @Mappings({
//            @Mapping(target = "id", source = "operation.identity"),
//
//            @Mapping(target = "accountId", source = "operation.account.identity"),
//            @Mapping(target = "accountTitle", source = "operation.account.title"),
//            @Mapping(target = "currency", source = "operation.account.currency"),
//
//            @Mapping(target = "categoryId", source = "operation.subcategory.parent.identity"),
//            @Mapping(target = "categoryTitle", source = "operation.subcategory.parent.title"),
//            @Mapping(target = "subcategoryId", source = "operation.subcategory.identity"),
//            @Mapping(target = "subcategoryTitle", source = "operation.subcategory.title"),
//    })
    OperationResponseDto fromUsecase(OperationDto operation);


    //    @Mappings({
//            @Mapping(target = "accountIdentity", source = "dto.accountId"),
//            @Mapping(target = "accountToIdentity", source = "dto.accountToId"),
//            @Mapping(target = "subcategoryIdentity", source = "dto.subcategoryId"),
//    })
    OperationCreateUsecase.RequestDto toCreateUsecase(OperationCreateRequestDto dto);


    OperationsCreateResponseDto fromCreateUsecase(OperationCreateUsecase.ResponseDto response);

//    @Mappings({
//            @Mapping(target = "operationIdentity", source = "dto.id"),
//    })
//    OperationCancelUsecase.Request toCancelUsecase(CommonIdDto dto);
//
//
//    @Mappings({
//            @Mapping(target = "canceledOperationsIds", source = "response.canceledOperationsIdentities"),
//    })
//    OperationsCancelResponseDto fromCancelUsecase(OperationCancelUsecase.Response response);

}
