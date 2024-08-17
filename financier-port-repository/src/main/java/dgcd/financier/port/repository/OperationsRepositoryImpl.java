package dgcd.financier.port.repository;

import dgcd.financier.core.domain.OperationType;
import dgcd.financier.core.domain.model.Operation;
import dgcd.financier.core.usecase.api.port.repository.OperationsRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
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

import static dgcd.financier.port.repository.AccountsRepositoryImpl.ACCOUNT_ROW_MAPPER;
import static dgcd.financier.port.repository.CategoriesRepositoryImpl.CATEGORY_ROW_MAPPER;
import static dgcd.financier.port.repository.utils.JdbcUtils.KEY_FIELD;
import static dgcd.financier.port.repository.utils.JdbcUtils.getEnum;
import static dgcd.financier.port.repository.utils.JdbcUtils.getLd;
import static dgcd.financier.port.repository.utils.JdbcUtils.getLong;
import static dgcd.financier.port.repository.utils.JdbcUtils.queryForObjectSafely;

@Slf4j
@Repository
@RequiredArgsConstructor
@SuppressWarnings({"DataFlowIssue", "OptionalGetWithoutIsPresent"})
public class OperationsRepositoryImpl implements OperationsRepository {

    private static final String SELECT_ALL = """
            select
            	o.id as o_id,
            	o."date" as o_date,
            	o.account_id as o_account_id,
            	o.quantity as o_quantity,
            	o.amount as o_amount,
            	o."type" as o_type,
            	o.subcategory_id as o_subcategory_id,
            	o."comment" as o_comment,
            	o.counterparty as o_counterparty,
            	o.correlation_id as o_correlation_id,
            	o.canceled as o_canceled,
                a.id as a_id,
                a.title as a_title,
                a.currency as a_currency,
                a.balance as a_balance,
                a.closed as a_closed,
            	c.id as c_id,
            	c.title as c_title,
            	c.parent_id as c_parent_id,
            	cp.id as cp_id,
            	cp.title as cp_title
            from operations o
            left join accounts a
            	on o.account_id = a.id
            left join categories c
            	on o.subcategory_id = c.id
            left join categories cp
            	on c.parent_id = cp.id""";

    private static final String SELECT_ALL_NOT_CANCELED = SELECT_ALL + " where o.canceled = false";

    private static final String SELECT_ALL_BY_CORRELATION = SELECT_ALL + " where o.correlation_id like :prefix";

    private static final String SELECT_BY_ID = SELECT_ALL + " where o.id = :id";

    private static final String INSERT = """
            insert into operations (
                "date",
                account_id,
                quantity,
                amount,
                "type",
                subcategory_id,
                "comment",
                counterparty,
                correlation_id
            ) values (
                :date,
                :account_id,
                :quantity,
                :amount,
                :type,
                :subcategory_id,
                :comment,
                :counterparty,
                :correlation_id
            )""";

    private static final String UPDATE = """
            update operations
            set
                date = :date,
                comment = :comment,
                counterparty = :counterparty,
                canceled = :canceled
            where id = :id""";


    private static final RowMapper<Operation> OPERATION_ROW_MAPPER = (rs, _) -> {
        var operation = new Operation()
                .setId(getLong(rs, "o_id"))
                .setDate(getLd(rs, "o_date"))
                .setAccountId(getLong(rs, "o_account_id"))
                .setAccount(ACCOUNT_ROW_MAPPER.mapRow(rs, 0))
                .setType(getEnum(rs, "o_type", OperationType.class))
                .setAmount(rs.getBigDecimal("o_amount"))
                .setQuantity(rs.getBigDecimal("o_quantity"))
                .setSubcategoryId(getLong(rs, "o_subcategory_id"))
                .setComment(rs.getString("o_comment"))
                .setCounterparty(rs.getString("o_counterparty"))
                .setCorrelationId(rs.getString("o_correlation_id"))
                .setCanceled(rs.getBoolean("o_canceled"));
        if (operation.getSubcategoryId() != null) {
            operation.setSubcategory(CATEGORY_ROW_MAPPER.mapRow(rs, 0));
        }
        return operation;
    };


    private final NamedParameterJdbcTemplate jdbcTemplate;


    @Override
    public List<Operation> findAllNotCanceled() {
        log.debug("[findAllNotCanceled] started");
        var operations = jdbcTemplate.query(SELECT_ALL_NOT_CANCELED, OPERATION_ROW_MAPPER);
        log.debug("[findAllNotCanceled] got operations: {}", operations);
        return operations;
    }


    @Override
    public Optional<Operation> findById(Long id) {
        log.debug("[findById] id: {}", id);
        var params = Map.of("id", id);
        var operationOpt = queryForObjectSafely(jdbcTemplate, SELECT_BY_ID, params, OPERATION_ROW_MAPPER);
        log.debug("[findById] operationOpt: {}", operationOpt);
        return operationOpt;
    }


    @Override
    public List<Operation> findByCorrelationIdStartingWith(String prefix) {
        log.debug("[findByCorrelationIdStartingWith] prefix: {}", prefix);
        var params = Map.of("prefix", prefix.concat("%"));
        var operations = jdbcTemplate.query(SELECT_ALL_BY_CORRELATION, params, OPERATION_ROW_MAPPER);
        log.debug("[findByCorrelationIdStartingWith] got operations: {}", operations);
        return operations;
    }


    @SneakyThrows
    @Override
    public Operation create(Operation operation) {
        log.debug("[create] operation: {}", operation);

        var params = new HashMap<String, Object>();
        params.put("date", operation.getDate());
        params.put("account_id", operation.getAccountId());
        params.put("quantity", operation.getQuantity());
        params.put("amount", operation.getAmount());
        params.put("type", operation.getType().name());
        params.put("subcategory_id", operation.getSubcategoryId());
        params.put("comment", operation.getComment());
        params.put("counterparty", operation.getCounterparty());
        params.put("correlation_id", operation.getCorrelationId());
        var paramSource = new MapSqlParameterSource(params);

        var keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(INSERT, paramSource, keyHolder, KEY_FIELD);
        long id = keyHolder.getKey().longValue();
        log.debug("[create] operation id: {}", id);

        return findById(id).get();
    }


    @Override
    public Operation update(Operation operation) {
        log.debug("[update] operation: {}", operation);

        var params = new HashMap<String, Object>();
        params.put("id", operation.getId());
        params.put("date", operation.getDate());
        params.put("comment", operation.getComment());
        params.put("counterparty", operation.getCounterparty());
        params.put("canceled", operation.isCanceled());

        var rows = jdbcTemplate.update(UPDATE, params);
        log.debug("[update] updated rows: {}", rows);

        return findById(operation.getId()).get();
    }


    @Override
    public void createAll(List<Operation> operations) {
        log.debug("[createAll] operations count: {}", operations.size());
        operations.forEach(this::create);
    }

}
