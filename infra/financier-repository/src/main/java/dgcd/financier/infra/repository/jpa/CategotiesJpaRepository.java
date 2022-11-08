package dgcd.financier.infra.repository.jpa;

import dgcd.financier.infra.repository.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategotiesJpaRepository extends JpaRepository<CategoryEntity, Long> {
}
