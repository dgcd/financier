package dgcd.financier.repository.impl;

import dgcd.financier.core.domain.Category;
import dgcd.financier.core.usecase.port.repository.CategoriesRepository;
import dgcd.financier.repository.jpa.CategotiesJpaRepository;
import dgcd.financier.repository.mapper.CategoryMapper;
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
