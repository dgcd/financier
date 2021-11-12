package dgcd.financier.app.service.dao;

import dgcd.financier.app.domain.model.Category;
import dgcd.financier.app.domain.repo.CategoriesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoriesDaoService {

    private final CategoriesRepository categoriesRepository;

    public List<Category> findAll() {
        return categoriesRepository.findAll();
    }

    public Optional<Category> findById(Long categoryId) {
        return categoriesRepository.findById(categoryId);
    }

    public Category save(Category category) {
        return categoriesRepository.save(category);
    }

}
