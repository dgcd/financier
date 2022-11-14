package dgcd.financier.test;

import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static io.zonky.test.db.AutoConfigureEmbeddedDatabase.DatabaseProvider.ZONKY;
import static io.zonky.test.db.AutoConfigureEmbeddedDatabase.DatabaseType.POSTGRES;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = {
        // todo: disabled until newer Flyway support
        "spring.flyway.enabled=false",
        "spring.jpa.hibernate.ddl-auto=none",
})
@AutoConfigureEmbeddedDatabase(provider = ZONKY, type = POSTGRES)
public class ZonkyTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Test
    void checkPostgresVersion() {
        var version = jdbcTemplate.queryForObject("select version();", String.class);

        assertThat(version).isNotNull();
        assertThat(version).contains("PostgreSQL 15.");
    }

}
