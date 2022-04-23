package dgcd.financier.app.modules.operation;

import org.springframework.data.jpa.repository.JpaRepository;

interface OperationsRepository extends JpaRepository<Operation, Long> {
}
