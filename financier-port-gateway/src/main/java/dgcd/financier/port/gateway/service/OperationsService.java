package dgcd.financier.port.gateway.service;

import dgcd.financier.core.usecase.api.OperationCancelUsecase;
import dgcd.financier.core.usecase.api.OperationCreateUsecase;
import dgcd.financier.core.usecase.api.OperationEditUsecase;
import dgcd.financier.core.usecase.api.error.CommonError;
import dgcd.financier.port.gateway.dto.CommonIdDto;
import dgcd.financier.port.gateway.dto.OperationCreateRequestDto;
import dgcd.financier.port.gateway.dto.OperationEditRequestDto;
import dgcd.financier.port.gateway.dto.OperationResponseDto;
import dgcd.financier.port.gateway.dto.OperationsCancelResponseDto;
import dgcd.financier.port.gateway.dto.OperationsCreateResponseDto;
import dgcd.financier.port.gateway.mapper.OperationDtoMapper;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static dgcd.financier.core.usecase.api.utils.EitherUtils.toRight;

@Service
@RequiredArgsConstructor
public class OperationsService {

    private final OperationDtoMapper operationDtoMapper;
    private final OperationCreateUsecase operationCreateUsecase;
    private final OperationEditUsecase operationEditUsecase;
    private final OperationCancelUsecase operationCancelUsecase;


    @Transactional
    public Either<CommonError, OperationsCreateResponseDto> createOperation(OperationCreateRequestDto dto) {
        return toRight(dto)
                .map(operationDtoMapper::toCreateUsecase)
                .flatMap(operationCreateUsecase::execute)
                .map(operationDtoMapper::fromCreateUsecase);
    }

    @Transactional
    public Either<CommonError, OperationResponseDto> editOperation(OperationEditRequestDto dto) {
        return toRight(dto)
                .map(operationDtoMapper::toEditUsecase)
                .flatMap(operationEditUsecase::execute)
                .map(operationDtoMapper::fromUsecase);
    }

    @Transactional
    public Either<CommonError, OperationsCancelResponseDto> cancelOperation(CommonIdDto dto) {
        return toRight(dto)
                .map(CommonIdDto::id)
                .flatMap(operationCancelUsecase::execute)
                .map(operationDtoMapper::fromCancelUsecase);
    }

}
