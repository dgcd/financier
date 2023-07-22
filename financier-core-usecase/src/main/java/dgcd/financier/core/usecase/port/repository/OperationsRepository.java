package dgcd.financier.core.usecase.port.repository;

import dgcd.financier.core.domain.Operation;

import java.util.List;
import java.util.Optional;

public interface OperationsRepository {

    List<Operation> findAll();

    List<Operation> findAllNotCanceled();

    Optional<Operation> findByIdentity(Long identity);

    List<Operation> findByCorrelationIdStartingWith(String prefix);

    Operation save(Operation operation);

    List<Operation> saveAll(List<Operation> operations);

}
