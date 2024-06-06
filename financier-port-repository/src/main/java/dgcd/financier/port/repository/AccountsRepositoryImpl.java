package dgcd.financier.port.repository;

import dgcd.financier.core.domain.CurrencyType;
import dgcd.financier.core.domain.model.Account;
import dgcd.financier.core.usecase.api.port.repository.AccountsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static dgcd.financier.port.repository.utils.JdbcUtils.KEY_FIELD;
import static dgcd.financier.port.repository.utils.JdbcUtils.getEnum;
import static dgcd.financier.port.repository.utils.JdbcUtils.getLong;
import static dgcd.financier.port.repository.utils.JdbcUtils.queryForObjectSafely;
import static java.util.Objects.requireNonNull;

@Slf4j
@Repository
@RequiredArgsConstructor
@SuppressWarnings({"DataFlowIssue", "OptionalGetWithoutIsPresent"})
public class AccountsRepositoryImpl implements AccountsRepository {

    private static final String SELECT_ALL = """
            select
                a.id as a_id,
                a.title as a_title,
                a.currency as a_currency,
                a.balance as a_balance,
                a.closed as a_closed
            from main.accounts a""";

    private static final String SELECT_BY_ID = SELECT_ALL + " where a.id = :id";

    private static final String COUNT_BY_TITLE = """
            select count(*)
            from main.accounts
            where title = :title""";

    private static final String INSERT = """
            insert into main.accounts (
                title,
                currency,
                balance,
                closed
            ) values (
                :title,
                :currency,
                :balance,
                :closed
            )""";

    private static final String UPDATE = """
            update main.accounts
            set
            	title   = :title,
            	balance = :balance,
            	closed  = :closed
            where id    = :id""";


    static final RowMapper<Account> ACCOUNT_ROW_MAPPER = (rs, _) -> new Account()
            .setId(getLong(rs, "a_id"))
            .setTitle(rs.getString("a_title"))
            .setCurrency(getEnum(rs, "a_currency", CurrencyType.class))
            .setBalance(rs.getBigDecimal("a_balance"))
            .setClosed(rs.getBoolean("a_closed"));


    private final NamedParameterJdbcTemplate jdbcTemplate;


    @Override
    public List<Account> findAll() {
        log.debug("[findAll] started");
        var accounts = jdbcTemplate.query(SELECT_ALL, ACCOUNT_ROW_MAPPER);
        log.debug("[findAll] got accounts: {}", accounts);
        return accounts;
    }


    @Override
    public Optional<Account> findById(Long id) {
        log.debug("[findById] id: {}", id);

        requireNonNull(id);
        var params = Map.of("id", id);

        var accountOpt = queryForObjectSafely(jdbcTemplate, SELECT_BY_ID, params, ACCOUNT_ROW_MAPPER);

        log.debug("[findById] accountOpt: {}", accountOpt);
        return accountOpt;
    }


    @Override
    public boolean existByTitle(String title) {
        log.debug("[existByTitle] title: {}", title);
        var params = Map.of("title", title);
        var count = jdbcTemplate.queryForObject(COUNT_BY_TITLE, params, Integer.class);
        log.debug("[existByTitle] count: {}", count);
        return count > 0;
    }


    @Override
    public Account create(Account account) {
        log.debug("[create] account: {}", account);

        var params = Map.of(
                "title", account.getTitle(),
                "currency", account.getCurrency().name(),
                "balance", account.getBalance(),
                "closed", account.isClosed()
        );
        var paramSource = new MapSqlParameterSource(params);
        var keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(INSERT, paramSource, keyHolder, KEY_FIELD);
        var id = keyHolder.getKey().longValue();
        log.debug("[create] account id: {}", id);

        return findById(id).get();
    }


    @Override
    public Account update(Account account) {
        log.debug("[update] account: {}", account);

        var params = Map.of(
                "id", account.getId(),
                "title", account.getTitle(),
                "balance", account.getBalance(),
                "closed", account.isClosed()
        );

        var rows = jdbcTemplate.update(UPDATE, params);
        log.debug("[update] updated rows: {}", rows);

        return findById(account.getId()).get();
    }


    @Override
    public List<Account> createAll(List<Account> accounts) {
        log.debug("[createAll] accounts count: {}", accounts.size());
        return accounts.stream()
                .map(this::create)
                .toList();
    }

}
