package dgcd.financier.infra.gateway.service;

import dgcd.financier.core.usecase.OperationCancelUsecase;
import dgcd.financier.core.usecase.OperationCreateUsecase;
import dgcd.financier.infra.gateway.dto.CommonIdDto;
import dgcd.financier.infra.gateway.dto.OperationCreateRequestDto;
import dgcd.financier.infra.gateway.dto.OperationsCancelResponseDto;
import dgcd.financier.infra.gateway.dto.OperationsCreateResponseDto;
import dgcd.financier.infra.gateway.mapper.OperationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OperationsService {

    private final OperationCreateUsecase operationCreateUsecase;
    private final OperationCancelUsecase operationCancelUsecase;
    private final OperationMapper operationMapper;


    @Transactional
    public OperationsCreateResponseDto createOperation(OperationCreateRequestDto dto) {
        var request = operationMapper.toCreateUsecase(dto);
        var response = operationCreateUsecase.execute(request);
        return operationMapper.fromCreateUsecase(response);
    }


    @Transactional
    public OperationsCancelResponseDto cancelOperation(CommonIdDto dto) {
        var request = operationMapper.toCancelUsecase(dto);
        var response = operationCancelUsecase.execute(request);
        return operationMapper.fromCancelUsecase(response);
    }

}
