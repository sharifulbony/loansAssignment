package com.shariful.loan;

import com.shariful.loan.configurations.Constants;
import com.shariful.loan.controllers.LoanController;
import com.shariful.loan.dtos.ApiSuccess;
import org.json.simple.JSONObject;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest(LoanController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RESTControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private LoanController loanController;
    String testInput1;

    @Test
    public void testLoanInsertAPI() throws Exception {
        JSONObject testJsonObject1 = new JSONObject();
        testJsonObject1.put("customerId", "12-1234-123");
        testJsonObject1.put("amount", "200");
        testJsonObject1.put("approvers", "a,b");
        testInput1 = testJsonObject1.toJSONString();
        mvc.perform(
                post("/" + Constants.baseURL + "/" + Constants.loanRequestURL)
                        .contentType(MediaType.APPLICATION_JSON).content(testInput1)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    public void testApprovalAPI() throws Exception {
        mvc.perform(post("/" + Constants.baseURL + "/" + Constants.loanApproveURL)
                .param("customerId","12-1234-123")
                .param("username","a")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        mvc.perform(post("/" + Constants.baseURL + "/" + Constants.loanApproveURL)
                .param("customerId","12-1234-123")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isExpectationFailed());
    }
    @Test
    public void testRejectionAPI() throws Exception {
        mvc.perform(post("/" + Constants.baseURL + "/" + Constants.loanApproveURL)
                .param("customerId","12-1234-123")
                .param("username","a")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        mvc.perform(post("/" + Constants.baseURL + "/" + Constants.loanApproveURL)
                .param("customerId","12-1234-123")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isExpectationFailed());
    }

    @Test
    public void testStatisticsAPI() throws Exception {
        mvc.perform(get("/" + Constants.baseURL + "/" + Constants.statisticsURL)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }
}
