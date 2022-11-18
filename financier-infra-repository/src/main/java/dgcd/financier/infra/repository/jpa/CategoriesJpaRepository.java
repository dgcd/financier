package dgcd.financier.infra.repository.jpa;

import dgcd.financier.infra.repository.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoriesJpaRepository extends JpaRepository<CategoryEntity, Long> {

    List<CategoryEntity> findByTitle(String title);

}
