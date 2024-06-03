package dgcd.financier.port.gateway.service;

import dgcd.financier.core.usecase.api.OperationCreateUsecase;
import dgcd.financier.core.usecase.api.error.CommonError;
import dgcd.financier.port.gateway.dto.OperationCreateRequestDto;
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
//    private final OperationCancelUsecase operationCancelUsecase;


    @Transactional
    public Either<CommonError, OperationsCreateResponseDto> createOperation(OperationCreateRequestDto dto) {
        return toRight(dto)
                .map(operationDtoMapper::toCreateUsecase)
                .flatMap(operationCreateUsecase::execute)
                .map(operationDtoMapper::fromCreateUsecase);
    }

//    @Transactional
//    public OperationsCancelResponseDto cancelOperation(CommonIdDto dto) {
//        var request = operationMapper.toCancelUsecase(dto);
//        var response = operationCancelUsecase.execute(request);
//        return operationMapper.fromCancelUsecase(response);
//    }

}
