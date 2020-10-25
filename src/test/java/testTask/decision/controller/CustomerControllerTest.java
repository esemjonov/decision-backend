package testTask.decision.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import testTask.decision.model.CreditScore;
import testTask.decision.model.Customer;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest()
@AutoConfigureMockMvc
class CustomerControllerTest {

    private static final String IDENTITY_CODE = "47777771234";
    private static final String CREDIT_MODIFIER = "100";

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    protected String toJson(Object Customer) throws JsonProcessingException {
        return objectMapper.writeValueAsString(Customer);
    }

    @Test
    void shouldBeGetAllCustomers() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/customers")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", equalTo(4)));
    }

    @Test
    void shouldBeSearchRestultIdentityCode() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/customers/49002010987")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].identityCode", equalTo("49002010987")));
    }

    @Test
    void shouldBeCustomerIdentityCodeEmpty() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/customers/47777010987")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    private Customer createCustomer() {
        return Customer.builder()
                .identityCode(IDENTITY_CODE)
                .creditModifier(CREDIT_MODIFIER)
                .build();
    }
}