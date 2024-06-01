package dgcd.financier.port.repository;

import dgcd.financier.core.api.port.repository.AccountsRepository;
import dgcd.financier.core.domain.CurrencyType;
import dgcd.financier.core.domain.model.Account;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import static dgcd.financier.port.repository.utils.JdbcUtils.getEnum;
import static dgcd.financier.port.repository.utils.JdbcUtils.getLong;

@Slf4j
@Repository
@RequiredArgsConstructor
public class AccountsRepositoryImpl implements AccountsRepository {

    private final static String[] keyField = new String[]{"id"};

    private static final String SELECT_ALL = """
            select
                a.id as a_id,
                a.title as a_title,
                a.currency as a_currency,
                a.balance as a_balance,
                a.closed as a_closed
            from main.accounts a""";

    private static final String COUNT_BY_TITLE = """
            select count(*)
            from main.accounts
            where title = :title""";

    private static final String INSERT = """
            insert into main.accounts (
                title,
                currency
            ) values (
                :title,
                :currency
            )""";


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

//    @Override
//    public Optional<Account> findById(Long id) {
//        log.debug("[findById] id: {}", id);
////        log.debug("[findById] account: {}", accountOpt.orElse(null));
//        return Optional.empty();
//    }


    @Override
    public boolean existByTitle(String title) {
        log.debug("[existByTitle] title: {}", title);
        var params = Map.of("title", title);
        var count = jdbcTemplate.queryForObject(COUNT_BY_TITLE, params, Integer.class);
        log.debug("[existByTitle] count: {}", count);
        return count > 0;
    }

    @Override
    public Account save(Account account) {
        log.debug("[save] account: {}", account);

        var paramSource = new MapSqlParameterSource(Map.of(
                "title", account.getTitle(),
                "currency", account.getCurrency().name()
        ));

        var keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(INSERT, paramSource, keyHolder, keyField);
        long id = keyHolder.getKey().longValue();
        log.debug("[save] account id: {}", id);
        return account.setId(id);
    }

//    @Override
//    public List<Account> saveAll(List<Account> accounts) {
//        log.debug("[saveAll] accounts count: {}", accounts.size());
////        if (log.isDebugEnabled()) {
////            savedAccounts.forEach(account -> log.debug("[saveAll] account: {}", account));
////        }
//        return Collections.emptyList();
//    }

}
