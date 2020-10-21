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
import testTask.decision.repository.CustomerRepository;
import testTask.decision.repository.ProductRepository;

import java.io.Serializable;
import java.util.List;

@Service
public class CreditScoreService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomerService customerService;

    private final CreditScoreRepository creditScoreRepository;

    public CreditScoreService(CreditScoreRepository creditScoreRepository) {
        this.creditScoreRepository = creditScoreRepository;
    }


    public CreditScore getById(Long id) throws CreditScoreNotFoundException {
        return creditScoreRepository.findById(id)
                .orElseThrow(CreditScoreNotFoundException::new);
    }

    public List<CreditScore> getAll() {
        return creditScoreRepository.findAll();
    }

    public CreditScore save(CreditScore creditScore) {
        Product product = productRepository.findById(1L)
                .orElseThrow(ProductNotFoundException::new);
        creditScore.setProduct(product);

        creditScore.setApprovedLoanPeriodMonths(
                creditScore.getLoanPeriodMonths() + CheckLoanPeriodMonths(creditScore,product));

        SetApprovedLoanAmount(creditScore);

        SetCreditApplicationStatus(creditScore);


        return creditScoreRepository.save(creditScore);
    }

    private void SetApprovedLoanAmount(CreditScore creditScore) {
        Integer correctionToLoanAmount = 0;

        if (creditScore.getLoanAmount() <= creditScore.getProduct().getLoanAmountMin()) {
            correctionToLoanAmount = creditScore.getProduct().getLoanAmountMin() - creditScore.getLoanAmount() ;
        }
        else if (creditScore.getLoanAmount() >= creditScore.getProduct().getLoanAmountMax()){
            correctionToLoanAmount = creditScore.getProduct().getLoanAmountMax() - creditScore.getLoanAmount();
        }
        creditScore.setApprovedLoanAmount(correctionToLoanAmount+ creditScore.getLoanAmount());
    }


    private Integer CheckLoanPeriodMonths (CreditScore creditScore, Product product) {
        Integer correctionToPeriod = 0;
        Integer applicationLoanPeriod = creditScore.getLoanPeriodMonths();
        Integer productLoanPeriodMin = product.getLoanPeriodMin();
        Integer productLoanPeriodMax = product.getLoanPeriodMax();

        if (applicationLoanPeriod < productLoanPeriodMin) {
            correctionToPeriod = applicationLoanPeriod-productLoanPeriodMin;
        }
        else if (applicationLoanPeriod > productLoanPeriodMax) {
            correctionToPeriod = productLoanPeriodMax - applicationLoanPeriod;
        }
        return correctionToPeriod;
    }



    private CreditScore SetCreditApplicationStatus(CreditScore creditScore){
        List<CustomerDto> customerDtoList = customerService.getByIdentityCode(creditScore.getIdentityCode());
        CustomerDto customer = customerDtoList.get(0); // TODO error handling
        if (customer.getCreditModifier().equals("debt")){
            creditScore.setCreditScore((double) 0);
            creditScore.setStatus("Dept");
            return creditScore;
        }

        Double calculatedCreditScore = Double.valueOf((Double.parseDouble(customer.getCreditModifier())/creditScore.getLoanAmount()) * creditScore.getLoanPeriodMonths());
        creditScore.setCreditScore(calculatedCreditScore);


        if (CheckPeriod(creditScore)) {

            creditScore.setStatus("Denied");
            return creditScore;
        }


        if (CheckAmount(creditScore)) {

            creditScore.setStatus("Denied");
            return creditScore;
        }


        if ((calculatedCreditScore >= 1.0) && (creditScore.getStatus().equals("string"))) {
            creditScore.setStatus("Accepted");
        }

        return creditScore;
    }

    private boolean CheckAmount(CreditScore creditScore) {
        if (creditScore.getLoanAmount() < creditScore.getProduct().getLoanAmountMin()
                ||   creditScore.getProduct().getLoanAmountMax() < creditScore.getLoanAmount()) {
            return true;
        }
        return false;
    }


    private Boolean CheckPeriod(CreditScore creditScore) {
        if (creditScore.getLoanPeriodMonths() < creditScore.getProduct().getLoanPeriodMin()
                || creditScore.getProduct().getLoanPeriodMax() < creditScore.getLoanPeriodMonths()   ) {
            return true;
        }
        return false;
    }




}
