package dgcd.financier.core.usecase.api.port.repository;

import dgcd.financier.core.domain.model.Operation;

import java.util.List;
import java.util.Optional;

public interface OperationsRepository {

    List<Operation> findAllNotCanceled();

    Optional<Operation> findById(Long id);

    List<Operation> findByCorrelationIdStartingWith(String prefix);

    Operation create(Operation operation);

    Operation update(Operation operation);

    void createAll(List<Operation> operations);

}
