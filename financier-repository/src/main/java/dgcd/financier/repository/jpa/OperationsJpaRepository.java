package dgcd.financier.repository.jpa;

import dgcd.financier.repository.entity.OperationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationsJpaRepository extends JpaRepository<OperationEntity, Long> {
}
