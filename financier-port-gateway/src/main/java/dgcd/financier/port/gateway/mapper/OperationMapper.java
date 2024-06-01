//package dgcd.financier.infra.gateway.mapper;
//
//import dgcd.financier.core.domain.Operation;
//import dgcd.financier.core.usecase.impl.OperationCancelUsecase;
//import dgcd.financier.core.usecase.impl.OperationCreateUsecase;
//import dgcd.financier.infra.gateway.dto.CommonIdDto;
//import dgcd.financier.infra.gateway.dto.OperationCreateRequestDto;
//import dgcd.financier.infra.gateway.dto.OperationResponseDto;
//import dgcd.financier.infra.gateway.dto.OperationsCancelResponseDto;
//import dgcd.financier.infra.gateway.dto.OperationsCreateResponseDto;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//import org.mapstruct.Mappings;
//
//import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
//
//@Mapper(
//        componentModel = SPRING,
//        uses = {AccountMapper.class, CategoryMapper.class}
//)
//public interface OperationMapper {
//
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
//    OperationResponseDto fromDomain(Operation operation);
//
//
//    @Mappings({
//            @Mapping(target = "accountIdentity", source = "dto.accountId"),
//            @Mapping(target = "accountToIdentity", source = "dto.accountToId"),
//            @Mapping(target = "subcategoryIdentity", source = "dto.subcategoryId"),
//    })
//    OperationCreateUsecase.Request toCreateUsecase(OperationCreateRequestDto dto);
//
//
//    OperationsCreateResponseDto fromCreateUsecase(OperationCreateUsecase.Response response);
//
//
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
//
//}
