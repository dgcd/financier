package dgcd.financier.infra.repository.impl;

import dgcd.financier.core.domain.Category;
import dgcd.financier.core.usecase.port.repository.CategoriesRepository;
import dgcd.financier.infra.repository.jpa.CategoriesJpaRepository;
import dgcd.financier.infra.repository.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CategoriesRepositoryImpl implements CategoriesRepository {

    private final CategoriesJpaRepository categoriesJpaRepository;


    @Override
    public List<Category> findAll() {
        return categoriesJpaRepository.findAll()
                .stream()
                .map(CategoryMapper::fromEntity)
                .toList();
    }


    @Override
    public List<Category> findAllByTitle(String title) {
        return categoriesJpaRepository.findByTitle(title)
                .stream()
                .map(CategoryMapper::fromEntity)
                .toList();
    }


    @Override
    public Optional<Category> findById(Long identity) {
        return categoriesJpaRepository
                .findById(identity)
                .map(CategoryMapper::fromEntity);
    }


    @Override
    public Category save(Category category) {
        var entity = CategoryMapper.toEntity(category);
        var savedEntity = categoriesJpaRepository.save(entity);
        return CategoryMapper.fromEntity(savedEntity);
    }


    @Override
    public List<Category> saveAll(List<Category> categories) {
        var categoryEntities = categories.stream()
                .map(CategoryMapper::toEntity)
                .toList();
        return categoriesJpaRepository.saveAll(categoryEntities)
                .stream()
                .map(CategoryMapper::fromEntity)
                .toList();
    }

}
