package dgcd.financier.infra.repository.jpa;

import dgcd.financier.infra.repository.entity.RatesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RatesJpaRepository extends JpaRepository<RatesEntity, Long> {

    Optional<RatesEntity> findFirstByOrderByDateDesc();

}
