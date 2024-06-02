package dgcd.financier.port.repository;

import dgcd.financier.core.domain.model.Category;
import dgcd.financier.core.usecase.api.port.repository.CategoriesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static dgcd.financier.port.repository.utils.JdbcUtils.getLong;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CategoriesRepositoryImpl implements CategoriesRepository {

    private static final String SELECT_ALL = """
            select
            	c.id as c_id,
            	c.title as c_title,
            	c.parent_id as c_parent_id,
            	cp.id as cp_id,
            	cp.title as cp_title
            from main.categories c
            left join main.categories cp
            	on c.parent_id = cp.id""";


    private static final RowMapper<Category> PARENT_ROW_MAPPER = (rs, _) -> new Category()
            .setId(getLong(rs, "cp_id"))
            .setTitle(rs.getString("cp_title"));

    static final RowMapper<Category> CATEGORY_ROW_MAPPER = (rs, _) -> {
        var category = new Category()
                .setId(getLong(rs, "c_id"))
                .setTitle(rs.getString("c_title"))
                .setParentId(getLong(rs, "c_parent_id"));
        if (category.getParentId() != null) {
            category.setParent(PARENT_ROW_MAPPER.mapRow(rs, 0));
        }
        return category;
    };


    private final NamedParameterJdbcTemplate jdbcTemplate;


    @Override
    public List<Category> findAll() {
        log.debug("[findAll] started");
        var categories = jdbcTemplate.query(SELECT_ALL, CATEGORY_ROW_MAPPER);
        log.debug("[findAll] got categories: {}", categories);
        return categories;
    }

//    @Override
//    public List<Category> findAllByTitle(String title) {
//        log.debug("[findAllByTitle] title: {}", title);
////        if (log.isDebugEnabled()) {
////            categories.forEach(category -> log.debug("[findAllByTitle] category: {}", category));
////        }
//        return Collections.emptyList();
//    }
//
//
//    @Override
//    public Optional<Category> findById(Long id) {
//        log.debug("[findById] id: {}", id);
////        log.debug("[findById] category: {}", categoryOpt.orElse(null));
//        return Optional.empty();
//    }
//
//
//    @Override
//    public Category save(Category category) {
//        log.debug("[save] category: {}", category);
////        log.debug("[save] category saved: {}", savedCategory);
//        return null;
//    }
//
//
//    @Override
//    public List<Category> saveAll(List<Category> categories) {
//        log.debug("[saveAll] categories count: {}", categories.size());
////        if (log.isDebugEnabled()) {
////            savedCategories.forEach(category -> log.debug("[saveAll] category: {}", category));
////        }
//        return Collections.emptyList();
//    }

}
