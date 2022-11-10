package dgcd.financier.testSupport;

import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.time.Instant;
import java.util.Properties;

@TestConfiguration
public class TestConfig {

    @Bean
    public BuildProperties buildProperties() {
        Properties entries = new Properties();
        entries.setProperty("time", Instant.now().toString());
        entries.setProperty("version", "test-version");
        return new BuildProperties(entries);
    }

}
