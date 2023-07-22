package dgcd.financier.infra.repository.impl;

import dgcd.financier.core.domain.Category;
import dgcd.financier.core.usecase.port.repository.CategoriesRepository;
import dgcd.financier.infra.repository.jpa.CategoriesJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static dgcd.financier.infra.repository.mapper.CategoryMapper.INSTANCE;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CategoriesRepositoryImpl implements CategoriesRepository {

    private final CategoriesJpaRepository categoriesJpaRepository;


    @Override
    public List<Category> findAll() {
        var categories = categoriesJpaRepository.findAll()
                .stream()
                .map(INSTANCE::fromEntity)
                .toList();

        log.debug("[findAll] categories size: {}", categories.size());

        return categories;
    }


    @Override
    public List<Category> findAllByTitle(String title) {
        log.debug("[findAllByTitle] title: {}", title);
        var categories = categoriesJpaRepository.findByTitle(title)
                .stream()
                .map(INSTANCE::fromEntity)
                .toList();

        if (log.isDebugEnabled()) {
            categories.forEach(category -> log.debug("[findAllByTitle] category: {}", category));
        }

        return categories;
    }


    @Override
    public Optional<Category> findByIdentity(Long identity) {
        log.debug("[findByIdentity] identity: {}", identity);

        var categoryOpt = categoriesJpaRepository
                .findById(identity)
                .map(INSTANCE::fromEntity);

        log.debug("[findByIdentity] category: {}", categoryOpt.orElse(null));

        return categoryOpt;
    }


    @Override
    public Category save(Category category) {
        log.debug("[save] category: {}", category);

        var entity = INSTANCE.toEntity(category);
        var savedEntity = categoriesJpaRepository.save(entity);
        var savedCategory = INSTANCE.fromEntity(savedEntity);

        log.debug("[save] category: {}", savedCategory);

        return savedCategory;
    }


    @Override
    public List<Category> saveAll(List<Category> categories) {
        var categoryEntities = categories.stream()
                .map(INSTANCE::toEntity)
                .toList();
        var savedCategories = categoriesJpaRepository.saveAll(categoryEntities)
                .stream()
                .map(INSTANCE::fromEntity)
                .toList();

        if (log.isDebugEnabled()) {
            savedCategories.forEach(category -> log.debug("[saveAll] category: {}", category));
        }

        return savedCategories;
    }

}
