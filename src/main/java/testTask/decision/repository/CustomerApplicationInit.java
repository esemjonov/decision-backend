package testTask.decision.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import testTask.decision.model.Customer;

import java.util.List;

@Component
public class CustomerApplicationInit implements CommandLineRunner {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public void run(String... args) throws Exception {
        
        
        
        List<Customer> customers = List.of(

                new Customer.builder().id(1l),"49002010965", "debt"),
                new Customer(2l,"49002010976", "100"),
                new Customer(3l,"49002010987", "300"),
                new Customer(4l,"49002010998", "1000")

        );
        customerRepository.saveAll(customers);
    }

}
