//package dgcd.financier.app.modules.initData;
//
//import dgcd.financier.app.testSupport.FinancierDatabaseTest;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.jdbc.Sql;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static dgcd.financier.app.infrastructure.web.WebConstants.INIT_PATH;
//import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
//import static org.springframework.http.MediaType.APPLICATION_JSON;
//import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@FinancierDatabaseTest
//class InitDataControllerTests {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Test
//    @Sql("/modules/initData/getInitData.sql")
//    void getInitData() throws Exception {
//        mockMvc
//                .perform(post(INIT_PATH)
//                        .contentType(APPLICATION_JSON)
//                        .accept(APPLICATION_JSON)
//                )
//                .andExpect(status().isOk())
//                .andExpect(header().string(CONTENT_TYPE, APPLICATION_JSON_VALUE))
//                .andExpect(jsonPath("$.payload.accounts[?(@.id == '1')].title").value("test test RUB"));
//    }
//
//}
