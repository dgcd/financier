package dgcd.financier.test.integration.config;

import dgcd.financier.test.support.queryCounter.QueryCountExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test_with_db")
//@Import(TestConfig.class)
@ExtendWith(QueryCountExtension.class)
public @interface FinancierIntegrationTest {

    String TEST_DATA_FOLDER = "/integration";

}
