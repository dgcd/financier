package dgcd.financier.infra.repository.jpa;

import dgcd.financier.infra.repository.entity.OperationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OperationsJpaRepository extends JpaRepository<OperationEntity, Long> {

    List<OperationEntity> findByIsCanceledFalse();

    List<OperationEntity> findByCorrelationIdStartingWith(String prefix);

}
