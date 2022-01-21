package dgcd.financier.app.domain.repo;

import dgcd.financier.app.domain.model.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationsRepository extends JpaRepository<Operation, Long> {
}
