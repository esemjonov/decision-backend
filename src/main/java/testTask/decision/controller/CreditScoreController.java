package testTask.decision.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import testTask.decision.repository.CustomerRepository;
import testTask.decision.service.CreditScoreService;

@RestController
@RequestMapping("/creditscore")
public class CreditScoreController {
    @Autowired
    private CustomerRepository customerRepository;


}
