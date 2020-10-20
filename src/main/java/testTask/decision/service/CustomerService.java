package testTask.decision.service;

import org.springframework.stereotype.Service;
import testTask.decision.exception.CustomerNotFoundException;
import testTask.decision.model.Customer;
import testTask.decision.repository.CustomerRepository;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer getById(Long id) throws  CustomerNotFoundException{
        return customerRepository.findById(id)
                .orElseThrow(CustomerNotFoundException::new);
    }

    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

}
