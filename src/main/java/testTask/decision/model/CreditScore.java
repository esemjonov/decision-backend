package testTask.decision.model;


import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Getter
@Setter
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "creditscore")
public class CreditScore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String identityCode;

    @NotNull
    private Integer loanAmount;

    @NotNull
    private Integer loanPeriodMonths;

    @NotNull
    private Double creditScore;

    @NotNull
    @OneToOne
    private Product product;
}
