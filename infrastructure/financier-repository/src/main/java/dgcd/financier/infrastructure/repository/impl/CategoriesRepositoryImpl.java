package dgcd.financier.infrastructure.repository.impl;

import dgcd.financier.core.domain.Category;
import dgcd.financier.core.usecase.port.repository.CategoriesRepository;
import dgcd.financier.infrastructure.repository.mapper.CategoryMapper;
import dgcd.financier.infrastructure.repository.jpa.CategotiesJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CategoriesRepositoryImpl implements CategoriesRepository {

    private final CategotiesJpaRepository categotiesJpaRepository;

    @Override
    public List<Category> findAll() {
        return categotiesJpaRepository.findAll()
                .stream()
                .map(CategoryMapper::fromEntity)
                .toList();
    }

}
