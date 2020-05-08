package com.adrixus.demo.service;

import com.adrixus.demo.domain.Customer;
import com.adrixus.demo.repository.CustomerRepository;
import com.adrixus.demo.web.rest.vm.SaveCustomerRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService{

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServiceImpl.class);

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository)
    {
        this.customerRepository = customerRepository;
    }

    @Override
    public Long saveCustomer(SaveCustomerRequest saveCustomerRequest)
    {
        Customer customer = new Customer();
        customer.setName(saveCustomerRequest.getName());
        customer.setAddressLine1(saveCustomerRequest.getAddressLine1());
        customer.setAddressLine2(saveCustomerRequest.getAddressLine2());
        customer.setCity(saveCustomerRequest.getCity());
        customer.setState(saveCustomerRequest.getState());
        customer.setCountry(saveCustomerRequest.getCountry());
        return customerRepository.save(customer).getId();
    }

    @Override
    public Optional<Customer> getCustomerById(Long id)
    {
        return customerRepository.findById(id);
    }
}
