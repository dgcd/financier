package dgcd.financier.app.modules.operation;

import dgcd.financier.app.modules.operation.dto.OperationCreateRequestDto;
import dgcd.financier.app.modules.operation.dto.OperationCreateResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OperationsService {

    private final OperationsDaoService operationsDaoService;

    @Transactional
    public OperationCreateResponseDto createOperation(OperationCreateRequestDto dto) {

        return new OperationCreateResponseDto(null, null);
    }

}
