package dgcd.financier.core.api.port.repository;

import dgcd.financier.core.domain.model.Category;

import java.util.List;

public interface CategoriesRepository {

    List<Category> findAll();

//    List<Category> findAllByTitle(String title);
//
//    Optional<Category> findById(Long id);
//
//    Category save(Category category);
//
//    List<Category> saveAll(List<Category> categories);

}
