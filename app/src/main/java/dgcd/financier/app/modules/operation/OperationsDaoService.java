package dgcd.financier.app.modules.operation;

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

    Operation save(Operation operation) {
        return operationsRepository.save(operation);
    }

    public List<Operation> saveAll(List<Operation> operations) {
        return operationsRepository.saveAll(operations);
    }

}
