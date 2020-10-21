package testTask.decision.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import testTask.decision.model.CreditScore;
import testTask.decision.model.Customer;
import testTask.decision.model.Product;
import testTask.decision.repository.CreditScoreRepository;
import testTask.decision.repository.CustomerRepository;
import testTask.decision.repository.ProductRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest()
@AutoConfigureMockMvc
class CreditScoreServiceTest {

    private static final Long ID = 1L;
    private static final String IDENTITY_CODE = "49002010976";
    private static final Integer LOAN_AMOUNT = 1000;
    private static final Integer LOAN_PERIOD_MONTHS = 20;
    private static final Double CREDIT_SCORE = 0.0;
    private static final String STATUS = "string";
    private static final Integer APPROVED_LOAN_AMOUNT = 0;
    private static final Integer APPROVED_LOAN_PERIOD_MONTHS = 0;
    private static final Product PRODUCT =
            Product.builder()
                    .id(1L)
                    .loanAmountMin(2000)
                    .loanAmountMax(10000)
                    .loanPeriodMin(12)
                    .loanPeriodMax(60)
            .build();




    @InjectMocks
    private CustomerService customerService;

    @InjectMocks
    private CreditScoreService creditScoreService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CreditScoreRepository creditScoreRepository;

    @Mock
    private CustomerRepository customerRepository;


    Customer customer = Customer.builder()
            .id(1L)
            .identityCode("49002010976")
            .creditModifier("100")
            .build();




    @Test
    void shouldSaveAccepted() {
        customerRepository.save(customer);

        /*
        CreditScore creditScore = createCreditScore();
        CreditScore savedCreditScore = createCreditScore().toBuilder().identityCode(IDENTITY_CODE).build();
        when(creditScoreRepository.save(creditScore)).thenReturn(savedCreditScore);
        CreditScore result = creditScoreService.save(creditScore);
        assertThat(result).isEqualTo(savedCreditScore);

        */
    }



    private CreditScore createCreditScore() {
        return CreditScore.builder()
                .id(ID)
                .identityCode(IDENTITY_CODE)
                .loanAmount(LOAN_AMOUNT)
                .loanPeriodMonths(LOAN_PERIOD_MONTHS)
                .creditScore(CREDIT_SCORE)
                .status(STATUS)
                .approvedLoanAmount(APPROVED_LOAN_AMOUNT)
                .approvedLoanPeriodMonths(APPROVED_LOAN_PERIOD_MONTHS)
                .product(PRODUCT)
                .build();

    }


}