package dgcd.financier.port.repository;

import dgcd.financier.core.usecase.api.port.repository.MiscRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MiscRepositoryImpl implements MiscRepository {

    private static final String COUNT_ALL = """
            select sum("count") from (
            	select count(*) from main.accounts
            	union
            	select count(*) from main.categories
            	union
            	select count(*) from main.operations
            )""";


    private final NamedParameterJdbcTemplate jdbcTemplate;


    @Override
    public boolean databaseIsEmpty() {
        log.debug("[countAll] started");
        var count = jdbcTemplate.queryForObject(COUNT_ALL, Map.of(), Integer.class);
        log.debug("[countAll] total rows in db: {}", count);
        return count == 0;
    }

}
