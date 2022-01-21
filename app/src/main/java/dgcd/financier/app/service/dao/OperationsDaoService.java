package dgcd.financier.app.service.dao;

import dgcd.financier.app.domain.model.Operation;
import dgcd.financier.app.domain.repo.OperationsRepository;
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

}
