package testTask.decision.service;

import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
public class CreditScoreService {


    public Serializable CalculateScore(String creditModifier,Integer loanAmount, Integer loanPeriod) {
        if (creditModifier == "dept")
            return "Denied";
        else
            return (Integer.parseInt(creditModifier)/loanAmount) * loanPeriod;
    }
}
