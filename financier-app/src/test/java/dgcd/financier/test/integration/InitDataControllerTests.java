package dgcd.financier.test.integration;

import dgcd.financier.test.integration.config.FinancierIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static dgcd.financier.infra.gateway.WebConstants.INIT_PATH;
import static dgcd.financier.test.integration.config.FinancierIntegrationTest.TEST_DATA_FOLDER;
import static dgcd.financier.test.support.queryCounter.AssertQueryCount.assertSelectCount;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@FinancierIntegrationTest
class InitDataControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Sql(TEST_DATA_FOLDER + "/initData/getInitData.sql")
    void getInitData() throws Exception {
        mockMvc
                .perform(post(INIT_PATH)
                        .accept(APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(CONTENT_TYPE, APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.payload.accounts", hasSize(1)))
                .andExpect(jsonPath("$.payload.accounts[?(@.id == '1')].title").value("test test RUB"))
                .andExpect(jsonPath("$.payload.categories", hasSize(2)))
                .andExpect(jsonPath("$.payload.operations", hasSize(3)));

        assertSelectCount(3);
    }

}
