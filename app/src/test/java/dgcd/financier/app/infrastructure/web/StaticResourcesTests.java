package dgcd.financier.app.infrastructure.web;

import dgcd.financier.app.testSupport.FinancierDatabaseTest;
import lombok.SneakyThrows;
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

@FinancierDatabaseTest
class StaticResourcesTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @SneakyThrows
    void getIndexHtml() {
        mockMvc
                .perform(get("/index.html"))
                .andExpect(status().isOk())
                .andExpect(header().string(CONTENT_TYPE, TEXT_HTML_VALUE))
                .andExpect(content().string(containsString("Financier Application")));
    }

    @Test
    @SneakyThrows
    void getFavicon() {
        mockMvc
                .perform(get("/favicon.ico"))
                .andExpect(status().isOk())
                .andExpect(header().string(CONTENT_TYPE, "image/x-icon"));
    }

}
