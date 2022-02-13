package dgcd.financier.app.modules.operation;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationsRepository extends JpaRepository<Operation, Long> {
}
