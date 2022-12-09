package dgcd.financier.test.integration.config;

import dgcd.financier.test.support.queryCounter.QueryCountExtension;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static io.zonky.test.db.AutoConfigureEmbeddedDatabase.DatabaseProvider.ZONKY;
import static io.zonky.test.db.AutoConfigureEmbeddedDatabase.DatabaseType.POSTGRES;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)

@SpringBootTest
@Tag("integration")
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("integrationTest")
@ExtendWith(QueryCountExtension.class)
@AutoConfigureEmbeddedDatabase(provider = ZONKY, type = POSTGRES)
public @interface FinancierIntegrationTest {

    String TEST_DATA_FOLDER = "/integration";

}
