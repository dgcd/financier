package dgcd.financier.repository.jpa;

import dgcd.financier.repository.entity.OperationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OperationsJpaRepository extends JpaRepository<OperationEntity, Long> {

    List<OperationEntity> findByIsCanceledFalse();

}
