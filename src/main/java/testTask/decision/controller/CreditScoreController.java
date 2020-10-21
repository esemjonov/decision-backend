package testTask.decision.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import testTask.decision.exception.CreditScoreNotFoundException;
import testTask.decision.model.CreditScore;
import testTask.decision.repository.CreditScoreRepository;
import testTask.decision.service.CreditScoreService;

import java.util.List;

@RestController
@RequestMapping(value = "creditscore", produces = MediaType.APPLICATION_JSON_VALUE)
public class CreditScoreController {

    private final CreditScoreService creditScoreService;

    public CreditScoreController(CreditScoreService creditScoreService) {
        this.creditScoreService = creditScoreService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("")
    public List<CreditScore> all() {
        return creditScoreService.getAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("{id}")
    public CreditScore findById(@PathVariable Long id) {
        return creditScoreService.getById(id);
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    CreditScore newCreditScore(@RequestBody CreditScore creditScore) {
        return creditScoreService.save(creditScore);
    }


}
