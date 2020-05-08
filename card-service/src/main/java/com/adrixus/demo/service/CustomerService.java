package com.adrixus.demo.service;


import java.util.concurrent.ExecutionException;

public interface CustomerService {

    Boolean verifyCustomerId(Long customerId) throws ExecutionException, InterruptedException;
}
