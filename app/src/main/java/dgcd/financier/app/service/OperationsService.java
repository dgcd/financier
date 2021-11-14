package dgcd.financier.app.service;

import dgcd.financier.app.dto.operation.OperationCreateRequestDto;
import dgcd.financier.app.dto.operation.OperationCreateResponseDto;
import dgcd.financier.app.service.dao.OperationsDaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OperationsService {

    private final OperationsDaoService operationsDaoService;

    @Transactional
    public OperationCreateResponseDto createOperation(OperationCreateRequestDto dto) {

        return null;
    }

}
