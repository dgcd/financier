package dgcd.financier.config;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HikariEagerLoader {

    private final HikariDataSource hikariDataSource;

    @SneakyThrows
    @PostConstruct
    public void init() {
        hikariDataSource.getConnection().close();
    }

}
