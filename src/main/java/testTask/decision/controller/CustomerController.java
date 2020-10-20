package testTask.decision.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import testTask.decision.dto.CustomerDto;
import testTask.decision.model.Customer;
import testTask.decision.repository.CustomerRepository;
import testTask.decision.service.CustomerService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "customers", produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("")
    public List<CustomerDto> all() {
        return customerService.getAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    Customer getCustomerById(@PathVariable Long id) {
        return customerService.getById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    Customer newCustomer(@RequestBody Customer customer) {
        return customerService.save(customer);
    }

}
