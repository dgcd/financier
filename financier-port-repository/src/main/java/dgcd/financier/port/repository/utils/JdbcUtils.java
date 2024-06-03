package dgcd.financier.port.repository.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

@Slf4j
public final class JdbcUtils {

    public static final String[] KEY_FIELD = new String[]{"id"};


    public static Long getLong(ResultSet rs, String fieldName) throws SQLException {
        requireNonNull(rs);
        requireNonNull(fieldName);

        var result = rs.getLong(fieldName);
        return rs.wasNull() ? null : result;
    }


    public static <T extends Enum<T>> T getEnum(ResultSet rs, String fieldName, Class<T> enumClass) throws SQLException {
        requireNonNull(rs);
        requireNonNull(fieldName);

        var value = rs.getString(fieldName);
        return value == null ? null : Enum.valueOf(enumClass, value);
    }


    public static LocalDate getLd(ResultSet rs, String fieldName) throws SQLException {
        requireNonNull(rs);
        requireNonNull(fieldName);

        var date = rs.getDate(fieldName);
        return isNull(date) ? null : date.toLocalDate();
    }


    public static <T> Optional<T> queryForObjectSafely(
            NamedParameterJdbcTemplate template,
            String queryString,
            Map<String, ?> params,
            RowMapper<T> rowMapper
    ) {
        requireNonNull(template);
        requireNonNull(queryString);
        requireNonNull(params);
        requireNonNull(rowMapper);

        try {
            return Optional.ofNullable(template.queryForObject(queryString, params, rowMapper));
        } catch (EmptyResultDataAccessException e) {
            log.error("queryForObjectSafely: {}", e.getMessage());
            return Optional.empty();
        }
    }

}
