package testTask.decision.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import testTask.decision.dto.CustomerDto;
import testTask.decision.exception.CreditScoreNotFoundException;
import testTask.decision.exception.ProductNotFoundException;
import testTask.decision.model.CreditScore;
import testTask.decision.model.Customer;
import testTask.decision.model.Product;
import testTask.decision.repository.CreditScoreRepository;
import testTask.decision.repository.ProductRepository;

import java.util.List;

@Service
public class CreditScoreService {
    private static final Double CREDDIT_SCORE_LIMIT = 1.0;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomerService customerService;

    private final CreditScoreRepository creditScoreRepository;

    public CreditScoreService(CreditScoreRepository creditScoreRepository) {
        this.creditScoreRepository = creditScoreRepository;
    }


    public List<CreditScore> getAll() {
        return creditScoreRepository.findAll();
    }

    public CreditScore save(CreditScore creditScore) {
        SetProductToCreditScore(creditScore);
        SetCreditApplicationStatus(creditScore);

        if (!creditScore.getStatus().equals("Dept")){
            SetApprovedValues(creditScore);
        }

        return creditScoreRepository.save(creditScore);
    }

    private void SetProductToCreditScore(CreditScore creditScore){
        Product product = productRepository.findById(1L).orElseThrow(ProductNotFoundException::new);
        creditScore.setProduct(product);
    }

    Integer ensureRange(Integer value, Integer min, Integer max) {
        return Math.min(Math.max(value, min), max);
    }

    private void SetApprovedValues (CreditScore creditScore) {
        CustomerDto customer = customerService.getByIdentityCode(creditScore.getIdentityCode()).get(0);
        Double creditModifier = Double.valueOf(customer.getCreditModifier());
        Integer newLoanAmount = ensureRange((int)(creditModifier * creditScore.getLoanPeriodMonths()), creditScore.getProduct().getLoanAmountMin(),creditScore.getProduct().getLoanAmountMax());
        creditScore.setApprovedLoanAmount(newLoanAmount);
        Integer newPeriod = ensureRange((int)(newLoanAmount/creditModifier),creditScore.getProduct().getLoanPeriodMin(),creditScore.getProduct().getLoanPeriodMax());
        creditScore.setApprovedLoanPeriodMonths(newPeriod);

    }

    private void SetCreditApplicationStatus(CreditScore creditScore){
        List<CustomerDto> foundCustomer = customerService.getByIdentityCode(creditScore.getIdentityCode());
        String creditModifier = "";

        if (foundCustomer.size() == 0 ){
            creditModifier = "debt";
        }
        else {
            creditModifier =  foundCustomer.get(0).getCreditModifier();
        }


        if (creditModifier.equals("debt")){
            SetDebtStatus(creditScore);
        }
        else {
            Double calculatedCreditScore = Double.valueOf((Double.parseDouble(creditModifier)/creditScore.getLoanAmount()) * creditScore.getLoanPeriodMonths());
            creditScore.setCreditScore(calculatedCreditScore);
            CheckPeriod(creditScore);
            CheckAmount(creditScore);
            if ((calculatedCreditScore >= CREDDIT_SCORE_LIMIT) && (creditScore.getStatus().equals("string"))) {
                creditScore.setStatus("Accepted");
            }
            else if (creditScore.getStatus().equals("string")) {
                creditScore.setStatus("Denied");
            }
        }
    }
    
    private void SetDebtStatus(CreditScore creditScore) {
        creditScore.setCreditScore((double) 0);
        creditScore.setStatus("Dept");
    }

    private void CheckAmount(CreditScore creditScore) {
        if (creditScore.getLoanAmount() < creditScore.getProduct().getLoanAmountMin()
                ||   creditScore.getProduct().getLoanAmountMax() < creditScore.getLoanAmount())
        {
            creditScore.setStatus("Denied");
        }

    }

    private void CheckPeriod(CreditScore creditScore) {
        if (creditScore.getLoanPeriodMonths() < creditScore.getProduct().getLoanPeriodMin()
                || creditScore.getProduct().getLoanPeriodMax() < creditScore.getLoanPeriodMonths()   )
        {
            creditScore.setStatus("Denied");
        }
    }
}
