package dgcd.financier.core.usecase.port.repository;

import dgcd.financier.core.domain.Category;

import java.util.List;

public interface CategoriesRepository {

    List<Category> findAll();

}
