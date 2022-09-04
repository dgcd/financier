package dgcd.financier.app.modules.operation;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface OperationsRepository extends JpaRepository<Operation, Long> {

    List<Operation> findByIsCanceledFalse();

    List<Operation> findByCorrelationIdStartingWith(String suffix);

}
