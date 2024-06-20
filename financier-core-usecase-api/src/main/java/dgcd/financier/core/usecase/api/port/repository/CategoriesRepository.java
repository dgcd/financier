package dgcd.financier.core.usecase.api.port.repository;

import dgcd.financier.core.domain.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoriesRepository {

    List<Category> findAll();

    List<Category> findAllByTitle(String title);

    Optional<Category> findById(Long id);

    Category create(Category category);

    Category update(Category category);

    List<Category> createAll(List<Category> categories);

}
