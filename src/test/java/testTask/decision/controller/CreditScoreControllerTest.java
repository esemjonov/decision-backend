package testTask.decision.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import testTask.decision.model.CreditScore;
import testTask.decision.model.Customer;
import testTask.decision.model.Product;
import testTask.decision.repository.CreditScoreRepository;
import testTask.decision.repository.CustomerRepository;
import testTask.decision.repository.ProductRepository;
import testTask.decision.service.CreditScoreService;
import testTask.decision.service.CustomerService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest()
@AutoConfigureMockMvc
class CreditScoreControllerTest {

    private static final String IDENTITY_CODE = "49002010976";
    private static final Integer LOAN_AMOUNT = 5000;
    private static final Integer LOAN_PERIOD_MONTHS = 20;
    private static final String STATUS = "string";
    private static final Integer APPROVED_LOAN_PERIOD_MONTHS = 0;
    private static final Integer APPROVED_LOAN_AMOUNT = 0;


    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    protected String toJson(Object creditScore) throws JsonProcessingException {
        return objectMapper.writeValueAsString(creditScore);
    }

    @Test
    void shouldBeInDebt() throws Exception {
        CreditScore savedCreditScore = createCreditScore().toBuilder().identityCode("49002010965").build();
        mockMvc.perform(MockMvcRequestBuilders.post("/creditscore")
                .contentType(APPLICATION_JSON)
                .content(toJson(savedCreditScore)))
                .andExpect(jsonPath("$.identityCode", equalTo(savedCreditScore.getIdentityCode())))
                .andExpect(jsonPath("$.status", equalTo("Dept")))
                .andExpect(jsonPath("$.creditScore", equalTo(0.0)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldBeStatusDeniedLowCreditScore() throws Exception {
        CreditScore savedCreditScore = createCreditScore().toBuilder().identityCode(IDENTITY_CODE).build();
        mockMvc.perform(MockMvcRequestBuilders.post("/creditscore")
                .contentType(APPLICATION_JSON)
                .content(toJson(savedCreditScore)))
                .andExpect(jsonPath("$.identityCode", equalTo(savedCreditScore.getIdentityCode())))
                .andExpect(jsonPath("$.status", equalTo("Denied")))
                .andExpect(jsonPath("$.creditScore", equalTo(0.4)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldBeStatusAccepted() throws Exception {
        CreditScore savedCreditScore = createCreditScore().toBuilder().loanAmount(2000).build();
        mockMvc.perform(MockMvcRequestBuilders.post("/creditscore")
                .contentType(APPLICATION_JSON)
                .content(toJson(savedCreditScore)))
                .andExpect(jsonPath("$.identityCode", equalTo(savedCreditScore.getIdentityCode())))
                .andExpect(jsonPath("$.status", equalTo("Accepted")))
                .andExpect(jsonPath("$.creditScore", equalTo(1.0)))
                .andExpect(status().isCreated());
    }


    @Test
    void shouldBeInvalidPeriodMin() throws Exception {
        CreditScore savedCreditScore = createCreditScore().toBuilder().identityCode("49002010998").loanAmount(2000).loanPeriodMonths(11).build();
        mockMvc.perform(MockMvcRequestBuilders.post("/creditscore")
                .contentType(APPLICATION_JSON)
                .content(toJson(savedCreditScore)))
                .andExpect(jsonPath("$.identityCode", equalTo(savedCreditScore.getIdentityCode())))
                .andExpect(jsonPath("$.status", equalTo("Denied")))
                .andExpect(jsonPath("$.creditScore", equalTo(5.5)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldBeInvalidPeriodMax() throws Exception {
        CreditScore savedCreditScore = createCreditScore().toBuilder().identityCode("49002010998").loanPeriodMonths(71).build();
        mockMvc.perform(MockMvcRequestBuilders.post("/creditscore")
                .contentType(APPLICATION_JSON)
                .content(toJson(savedCreditScore)))
                .andExpect(jsonPath("$.identityCode", equalTo(savedCreditScore.getIdentityCode())))
                .andExpect(jsonPath("$.status", equalTo("Denied")))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldBeInvalidLoanAmountMin() throws Exception {
        CreditScore savedCreditScore = createCreditScore().toBuilder().loanAmount(1000).build();
        mockMvc.perform(MockMvcRequestBuilders.post("/creditscore")
                .contentType(APPLICATION_JSON)
                .content(toJson(savedCreditScore)))
                .andExpect(jsonPath("$.loanAmount", equalTo(savedCreditScore.getLoanAmount())))
                .andExpect(jsonPath("$.status", equalTo("Denied")))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldBeInvalidLoanAmountMax() throws Exception {
        CreditScore savedCreditScore = createCreditScore().toBuilder().loanAmount(11000).build();
        mockMvc.perform(MockMvcRequestBuilders.post("/creditscore")
                .contentType(APPLICATION_JSON)
                .content(toJson(savedCreditScore)))
                .andExpect(jsonPath("$.loanAmount", equalTo(savedCreditScore.getLoanAmount())))
                .andExpect(jsonPath("$.status", equalTo("Denied")))
                .andExpect(status().isCreated());
    }


    private CreditScore createCreditScore() {
        return CreditScore.builder()
                .identityCode(IDENTITY_CODE)
                .loanAmount(LOAN_AMOUNT)
                .loanPeriodMonths(LOAN_PERIOD_MONTHS)
                .status(STATUS)
                .approvedLoanAmount(APPROVED_LOAN_AMOUNT)
                .approvedLoanPeriodMonths(APPROVED_LOAN_PERIOD_MONTHS)
                .build();

    }
}