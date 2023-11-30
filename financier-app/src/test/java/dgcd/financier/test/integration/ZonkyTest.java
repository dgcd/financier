package dgcd.financier.test.integration;

import dgcd.financier.test.integration.config.FinancierIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@FinancierIntegrationTest
public class ZonkyTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value("${app.test.database.version-string}")
    private String databaseVersion;


    @Test
    void test_checkPostgresVersion_OK() {
        var version = jdbcTemplate.queryForObject("select version();", String.class);

        assertThat(version).isNotNull();
        assertThat(version).contains(databaseVersion);
    }

}
