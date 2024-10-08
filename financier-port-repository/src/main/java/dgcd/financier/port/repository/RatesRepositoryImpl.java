package dgcd.financier.port.repository;

import dgcd.financier.core.domain.model.Rate;
import dgcd.financier.core.usecase.api.port.repository.RatesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static dgcd.financier.port.repository.utils.JdbcUtils.getLd;
import static dgcd.financier.port.repository.utils.JdbcUtils.queryForObjectSafely;

@Slf4j
@Repository
@RequiredArgsConstructor
public class RatesRepositoryImpl implements RatesRepository {

    private static final String SELECT_LATEST = """
            select * from rates r
            order by r."date" desc
            limit 1""";

    private static final String SELECT_ALL = """
            select * from rates""";

    private static final String UPSERT = """
            insert into rates (
                "date",
                usd,
                eur
            ) values (
                :date,
                :usd,
                :eur
            )
            on conflict ("date") do
            update set
                usd = :usd,
                eur = :eur""";

    private static final RowMapper<Rate> ROW_MAPPER = (rs, _) -> new Rate()
            .setDate(getLd(rs, "date"))
            .setUsd(rs.getBigDecimal("usd"))
            .setEur(rs.getBigDecimal("eur"));


    private final NamedParameterJdbcTemplate jdbcTemplate;


    @Override
    public List<Rate> findAll() {
        log.debug("[findAll] started");
        var rates = jdbcTemplate.query(SELECT_ALL, ROW_MAPPER);
        log.debug("[findAll] rates size: {}", rates.size());
        log.debug("[findAll] got rates: {}", rates);
        return rates;
    }


    @Override
    public Optional<Rate> getLastRate() {
        log.debug("[getLastRate] started");
        var rate = queryForObjectSafely(jdbcTemplate, SELECT_LATEST, Map.of(), ROW_MAPPER);
        log.debug("[getLastRate] got rate: {}", rate);
        return rate;
    }


    @Override
    public void createOrUpdate(Rate rate) {
        log.debug("[createOrUpdate] rate: {}", rate);

        var params = new HashMap<String, Object>();
        params.put("date", rate.getDate());
        params.put("usd", rate.getUsd());
        params.put("eur", rate.getEur());

        jdbcTemplate.update(UPSERT, params);
        log.debug("[createOrUpdate] done");
    }


    @Override
    public void createAll(List<Rate> rates) {
        log.debug("[createAll] rates count: {}", rates.size());
        rates.forEach(this::createOrUpdate);
    }

}
