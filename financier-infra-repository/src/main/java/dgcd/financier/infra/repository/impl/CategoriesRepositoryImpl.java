package dgcd.financier.infra.repository.impl;

import dgcd.financier.core.domain.Category;
import dgcd.financier.core.usecase.port.repository.CategoriesRepository;
import dgcd.financier.infra.repository.jpa.CategoriesJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static dgcd.financier.infra.repository.mapper.CategoryMapper.INSTANCE;

@Repository
@RequiredArgsConstructor
public class CategoriesRepositoryImpl implements CategoriesRepository {

    private final CategoriesJpaRepository categoriesJpaRepository;


    @Override
    public List<Category> findAll() {
        return categoriesJpaRepository.findAll()
                .stream()
                .map(INSTANCE::fromEntity)
                .toList();
    }


    @Override
    public List<Category> findAllByTitle(String title) {
        return categoriesJpaRepository.findByTitle(title)
                .stream()
                .map(INSTANCE::fromEntity)
                .toList();
    }


    @Override
    public Optional<Category> findById(Long identity) {
        return categoriesJpaRepository
                .findById(identity)
                .map(INSTANCE::fromEntity);
    }


    @Override
    public Category save(Category category) {
        var entity = INSTANCE.toEntity(category);
        var savedEntity = categoriesJpaRepository.save(entity);
        return INSTANCE.fromEntity(savedEntity);
    }


    @Override
    public List<Category> saveAll(List<Category> categories) {
        var categoryEntities = categories.stream()
                .map(INSTANCE::toEntity)
                .toList();
        return categoriesJpaRepository.saveAll(categoryEntities)
                .stream()
                .map(INSTANCE::fromEntity)
                .toList();
    }

}
