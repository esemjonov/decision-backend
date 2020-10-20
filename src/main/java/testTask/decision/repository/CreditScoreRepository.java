package testTask.decision.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import testTask.decision.model.CreditScore;

@Repository
public interface CreditScoreRepository extends JpaRepository<CreditScore, Long> {
}
