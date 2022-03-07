package dgcd.financier.app.modules.category;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoriesRepository extends JpaRepository<Category, Long> {

    List<Category> findByTitle(String title);

}
