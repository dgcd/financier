package dgcd.financier.app.modules.operation;

import dgcd.financier.app.modules.operation.exceptions.OperationNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OperationsDaoService {

    private final OperationsRepository operationsRepository;

    public List<Operation> findAll() {
        return operationsRepository.findAll();
    }

    public List<Operation> findAllNotCanceled() {
        return operationsRepository.findByIsCanceledFalse();
    }

    Operation save(Operation operation) {
        return operationsRepository.save(operation);
    }

    public List<Operation> saveAll(List<Operation> operations) {
        return operationsRepository.saveAll(operations);
    }

    public Operation findByIdOrElseThrow(Long operationId) {
        return operationsRepository.findById(operationId)
                .orElseThrow(() -> new OperationNotFoundException(operationId));
    }

    List<Operation> findByCorrelationIdStartingWith(String suffix) {
        return operationsRepository.findByCorrelationIdStartingWith(suffix);
    }

}
