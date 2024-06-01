package dgcd.financier.port.repository;

import dgcd.financier.core.api.port.repository.RatesRepository;
import dgcd.financier.core.domain.model.Rate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

import static dgcd.financier.port.repository.utils.JdbcUtils.getLd;
import static dgcd.financier.port.repository.utils.JdbcUtils.queryForObjectSafely;

@Slf4j
@Repository
@RequiredArgsConstructor
public class RatesRepositoryImpl implements RatesRepository {

    private static final String SELECT_LATEST = """
            select * from main.rates r
            order by r."date" desc
            limit 1
            """;

    private static final RowMapper<Rate> ROW_MAPPER = (rs, _) -> new Rate()
            .setDate(getLd(rs, "date"))
            .setUsd(rs.getBigDecimal("usd"))
            .setEur(rs.getBigDecimal("eur"));


    private final NamedParameterJdbcTemplate jdbcTemplate;

//    @Override
//    public List<Rate> findAll() {
//        log.debug("[findAll] rates");
////        log.debug("[findAll] rates size: {}", rate.size());
//        return Collections.emptyList();
//    }

    @Override
    public Optional<Rate> getLastRate() {
        log.debug("[getLastRate] started");
        var rate = queryForObjectSafely(jdbcTemplate, SELECT_LATEST, Map.of(), ROW_MAPPER);
        log.debug("[getLastRate] got rate: {}", rate);
        return rate;
    }

//    @Override
//    public void save(Rate rate) {
////        log.debug("[updateRates] rates: {}", savedEntity);
//    }
//
//
//    @Override
//    public List<Rate> saveAll(List<Rate> rates) {
//        log.debug("[saveAll] rates");
//        return Collections.emptyList();
//    }

}