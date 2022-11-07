package dgcd.financier.core.usecase.port.repository;

import dgcd.financier.core.domain.Operation;

import java.util.List;

public interface OperationsRepository {

    List<Operation> findAllNotCanceled();

}
