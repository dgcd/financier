package dgcd.financier.repository.jpa;

import dgcd.financier.repository.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategotiesJpaRepository extends JpaRepository<CategoryEntity, Long> {
}
