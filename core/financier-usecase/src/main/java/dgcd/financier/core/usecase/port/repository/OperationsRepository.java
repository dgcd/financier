package dgcd.financier.core.usecase.port.repository;

import dgcd.financier.core.domain.Operation;

import java.util.List;
import java.util.Optional;

public interface OperationsRepository {

    List<Operation> findAllNotCanceled();

    Operation save(Operation operation);

    Optional<Operation> findByIdentity(Long dentity);

    List<Operation> findByCorrelationIdStartingWith(String prefix);

}
