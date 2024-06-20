package dgcd.financier.port.repository;

import dgcd.financier.core.domain.model.Category;
import dgcd.financier.core.usecase.api.port.repository.CategoriesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static dgcd.financier.port.repository.utils.JdbcUtils.KEY_FIELD;
import static dgcd.financier.port.repository.utils.JdbcUtils.getLong;
import static dgcd.financier.port.repository.utils.JdbcUtils.queryForObjectSafely;
import static java.util.Objects.requireNonNull;

@Slf4j
@Repository
@RequiredArgsConstructor
@SuppressWarnings({"DataFlowIssue", "OptionalGetWithoutIsPresent"})
public class CategoriesRepositoryImpl implements CategoriesRepository {

    private static final String SELECT_ALL = """
            select
            	c.id as c_id,
            	c.title as c_title,
            	c.parent_id as c_parent_id,
            	cp.id as cp_id,
            	cp.title as cp_title
            from categories c
            left join categories cp
            	on c.parent_id = cp.id""";

    private static final String SELECT_BY_ID = SELECT_ALL + " where c.id = :id";

    private static final String SELECT_BY_TITLE = SELECT_ALL + " where c.title = :title";

    private static final String INSERT = """
            insert into categories (
                title,
                parent_id
            ) values (
                :title,
                :parent_id
            )""";

    private static final String UPDATE = """
            update categories
            set
            	title = :title
            where id = :id""";


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


    @Override
    public List<Category> findAllByTitle(String title) {
        log.debug("[findAllByTitle] title: {}", title);

        requireNonNull(title);
        var params = Map.of("title", title);

        var categories = jdbcTemplate.query(SELECT_BY_TITLE, params, CATEGORY_ROW_MAPPER);
        log.debug("[findAllByTitle] got categories: {}", categories);
        return categories;
    }


    @Override
    public Optional<Category> findById(Long id) {
        log.debug("[findById] id: {}", id);

        requireNonNull(id);
        var params = Map.of("id", id);

        var categoryOpt = queryForObjectSafely(jdbcTemplate, SELECT_BY_ID, params, CATEGORY_ROW_MAPPER);

        log.debug("[findById] categoryOpt: {}", categoryOpt);
        return categoryOpt;
    }


    @Override
    public Category create(Category category) {
        log.debug("[create] category: {}", category);

        var params = new HashMap<String, Object>();
        params.put("title", category.getTitle());
        params.put("parent_id", category.getParentId());
        var paramSource = new MapSqlParameterSource(params);
        var keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(INSERT, paramSource, keyHolder, KEY_FIELD);
        var id = keyHolder.getKey().longValue();
        log.debug("[create] category id: {}", id);

        return findById(id).get();
    }


    @Override
    public Category update(Category category) {
        log.debug("[update] category: {}", category);

        var params = Map.of(
                "id", category.getId(),
                "title", category.getTitle()
        );

        var rows = jdbcTemplate.update(UPDATE, params);
        log.debug("[update] updated rows: {}", rows);

        return findById(category.getId()).get();
    }


    @Override
    public List<Category> createAll(List<Category> categories) {
        log.debug("[saveAll] categories count: {}", categories.size());
        return categories.stream()
                .map(this::create)
                .toList();
    }

}
