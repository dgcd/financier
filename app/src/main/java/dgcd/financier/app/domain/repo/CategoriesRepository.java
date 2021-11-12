package dgcd.financier.app.domain.repo;

import dgcd.financier.app.domain.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriesRepository extends JpaRepository<Category, Long> {

//    Optional<Account> findByTitle(String title);

}
