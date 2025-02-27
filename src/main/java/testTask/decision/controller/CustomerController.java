package testTask.decision.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import testTask.decision.dto.CustomerDto;
import testTask.decision.model.Customer;
import testTask.decision.service.CustomerService;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "customers", produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("")
    public List<Customer> all() {
        return customerService.getAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    Customer newCustomer(@RequestBody Customer customer) {
        return customerService.save(customer);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{identityCode}")
    List<CustomerDto> getByIdentityCode(@PathVariable String identityCode) {
        return customerService.getByIdentityCode(identityCode);
    }
}
