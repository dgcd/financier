package dgcd.financier.port.repository;

import dgcd.financier.core.api.port.repository.AccountsRepository;
import dgcd.financier.core.domain.CurrencyType;
import dgcd.financier.core.domain.model.Account;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static dgcd.financier.port.repository.utils.JdbcUtils.getEnum;
import static dgcd.financier.port.repository.utils.JdbcUtils.getLong;

@Slf4j
@Repository
@RequiredArgsConstructor
public class AccountsRepositoryImpl implements AccountsRepository {

    private static final String SELECT_ALL = """
            select
                a.id as a_id,
                a.title as a_title,
                a.currency as a_currency,
                a.balance as a_balance,
                a.closed as a_closed
            from main.accounts a""";

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
//
//
//    @Override
//    public boolean existByTitle(String title) {
//        log.debug("[existByTitle] title: {}", title);
////        log.debug("[existByTitle] isPresent: {}", isPresent);
//        return false;
//    }
//
//
//    @Override
//    public Account save(Account account) {
//        log.debug("[save] account: {}", account);
////        log.debug("[save] account saved: {}", savedAccount);
//        return null;
//    }
//
//
//    @Override
//    public List<Account> saveAll(List<Account> accounts) {
//        log.debug("[saveAll] accounts count: {}", accounts.size());
////        if (log.isDebugEnabled()) {
////            savedAccounts.forEach(account -> log.debug("[saveAll] account: {}", account));
////        }
//        return Collections.emptyList();
//    }

}
