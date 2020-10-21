package testTask.decision.service;

import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import testTask.decision.dto.CustomerDto;
import testTask.decision.exception.CustomerNotFoundException;
import testTask.decision.model.Customer;
import testTask.decision.repository.CustomerRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    private ModelMapper modelMapper;

    private CustomerDto convertToDto(Customer customer) {
        return modelMapper.map(customer, CustomerDto.class);
    }

    public Customer getById(Long id) throws  CustomerNotFoundException{
        return customerRepository.findById(id)
                .orElseThrow(CustomerNotFoundException::new);
    }

    public List<CustomerDto> getByIdentityCode(String identityCode) throws  CustomerNotFoundException{
        return customerRepository.findAllByIdentityCodeLike(identityCode).stream()
                .map(this::convertCustomer)
                .collect(Collectors.toList());
    }


    private CustomerDto convertCustomer(Customer customer) {
        CustomerDto dto = new CustomerDto();
        dto.setId(customer.getId());
        dto.setIdentitycode(customer.getIdentityCode());
        dto.setCreditModifier(customer.getCreditModifier());
        return dto;
    }
    

    public List<Customer> getAll() {
        return customerRepository.findAll();

    }




    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

}
