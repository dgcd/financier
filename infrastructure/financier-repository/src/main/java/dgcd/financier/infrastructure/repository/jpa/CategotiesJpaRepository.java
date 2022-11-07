package dgcd.financier.infrastructure.repository.jpa;

import dgcd.financier.infrastructure.repository.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategotiesJpaRepository extends JpaRepository<CategoryEntity, Long> {
}
