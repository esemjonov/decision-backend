package testTask.decision.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import testTask.decision.exception.CreditScoreRepositoryNotFoundException;
import testTask.decision.model.CreditScore;
import testTask.decision.model.Customer;
import testTask.decision.repository.CreditScoreRepository;
import testTask.decision.repository.CustomerRepository;
import testTask.decision.service.CreditScoreService;

import java.util.List;

@RestController
@RequestMapping("/creditscore")
public class CreditScoreController {

    @Autowired
    private CreditScoreRepository creditScoreRepository;



    @ResponseStatus(HttpStatus.OK)
    @GetMapping("")
    public List<CreditScore> all() {
        return creditScoreRepository.findAll();
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("{id}")
    public CreditScore findById(@PathVariable Long id) {
        return creditScoreRepository.findById(id)
                .orElseThrow(CreditScoreRepositoryNotFoundException::new);
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    CreditScore newCreditScore(@RequestBody CreditScore creditScore) {
        return creditScoreRepository.save(creditScore);
    }


}
