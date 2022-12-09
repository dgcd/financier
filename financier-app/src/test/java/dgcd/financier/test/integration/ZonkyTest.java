package dgcd.financier.test.integration;

import dgcd.financier.test.integration.config.FinancierIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;

//@SpringBootTest(properties = {
//        // todo: disabled until newer Flyway support
////        "spring.flyway.enabled=false",
////        "spring.jpa.hibernate.ddl-auto=none",
//})
//@AutoConfigureEmbeddedDatabase(provider = ZONKY, type = POSTGRES)
@FinancierIntegrationTest

public class ZonkyTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Test
    void checkPostgresVersion() {
        var version = jdbcTemplate.queryForObject("select version();", String.class);

        assertThat(version).isNotNull();
        assertThat(version).contains("PostgreSQL 15.1");
    }

}
