package dgcd.financier.test.integration;

import dgcd.financier.test.integration.config.FinancierIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.TEXT_HTML_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@FinancierIntegrationTest
class StaticResourcesTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getIndexHtml() throws Exception {
        mockMvc
                .perform(get("/index.html"))
                .andExpect(status().isOk())
                .andExpect(header().string(CONTENT_TYPE, TEXT_HTML_VALUE))
                .andExpect(content().string(containsString("Financier Application")));
    }


    @Test
    void getFavicon() throws Exception {
        mockMvc
                .perform(get("/favicon.ico"))
                .andExpect(status().isOk())
                .andExpect(header().string(CONTENT_TYPE, "image/x-icon"));
    }

}
