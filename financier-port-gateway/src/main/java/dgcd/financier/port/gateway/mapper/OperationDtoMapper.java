package dgcd.financier.port.gateway.mapper;

import dgcd.financier.core.usecase.api.OperationCancelUsecase;
import dgcd.financier.core.usecase.api.OperationCreateUsecase;
import dgcd.financier.core.usecase.api.OperationEditUsecase;
import dgcd.financier.core.usecase.api.dto.common.OperationDto;
import dgcd.financier.port.gateway.dto.OperationCreateRequestDto;
import dgcd.financier.port.gateway.dto.OperationEditRequestDto;
import dgcd.financier.port.gateway.dto.OperationResponseDto;
import dgcd.financier.port.gateway.dto.OperationsCancelResponseDto;
import dgcd.financier.port.gateway.dto.OperationsCreateResponseDto;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface OperationDtoMapper {

    OperationResponseDto fromUsecase(OperationDto operation);


    OperationCreateUsecase.RequestDto toCreateUsecase(OperationCreateRequestDto dto);

    OperationsCreateResponseDto fromCreateUsecase(OperationCreateUsecase.ResponseDto response);


    OperationEditUsecase.RequestDto toEditUsecase(OperationEditRequestDto dto);


    OperationsCancelResponseDto fromCancelUsecase(OperationCancelUsecase.ResponseDto response);

}
