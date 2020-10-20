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
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String identityCode;

    @NotNull
    private String creditModifier;



    /*
    public Customer(String identitycode, String credditModifer) {
        this.identitycode = identitycode;
        this.credditModifer = credditModifer;
    }

    public Customer() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentitycode() {
        return identitycode;
    }

    public void setIdentitycode(String identitycode) {
        this.identitycode = identitycode;
    }

    public String getCredditModifer() {
        return credditModifer;
    }

    public void setCredditModifer(String credditModifer) {
        this.credditModifer = credditModifer;
    }

     */
}
