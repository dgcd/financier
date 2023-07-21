package dgcd.financier.core.usecase.port.repository;

import dgcd.financier.core.domain.Category;

import java.util.List;
import java.util.Optional;

public interface CategoriesRepository {

    List<Category> findAll();

    List<Category> findAllByTitle(String title);

    Optional<Category> findByIdentity(Long identity);

    Category save(Category category);

    List<Category> saveAll(List<Category> categories);

}
