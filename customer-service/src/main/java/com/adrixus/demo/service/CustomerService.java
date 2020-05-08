package com.adrixus.demo.service;


import com.adrixus.demo.domain.Customer;
import com.adrixus.demo.web.rest.vm.SaveCustomerRequest;

import java.util.Optional;

public interface CustomerService {

    Long saveCustomer(SaveCustomerRequest saveCustomerRequest);

    Optional<Customer> getCustomerById(Long id);
}
