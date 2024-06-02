package dgcd.financier.core.usecase.api.port.repository;

import dgcd.financier.core.domain.model.Operation;

import java.util.List;

public interface OperationsRepository {

    List<Operation> findAllNotCanceled();

//    List<Operation> findAll();
//    Optional<Operation> findById(Long id);
//
//    List<Operation> findByCorrelationIdStartingWith(String prefix);
//
//    Operation save(Operation operation);
//
//    List<Operation> saveAll(List<Operation> operations);

}
