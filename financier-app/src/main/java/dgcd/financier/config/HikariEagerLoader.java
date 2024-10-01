package dgcd.financier.config;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

@Component
@RequiredArgsConstructor
public class HikariEagerLoader {

    private final HikariDataSource hikariDataSource;

    @PostConstruct
    public void init() throws SQLException {
        hikariDataSource.getConnection().close();
    }

}
