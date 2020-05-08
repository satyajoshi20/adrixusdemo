package com.adrixus.demo.service;


import com.adrixus.demo.web.rest.vm.AccountDto;
import com.adrixus.demo.web.rest.vm.SaveAccountRequest;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.concurrent.ExecutionException;

public interface AccountService {
    Long saveAccount(SaveAccountRequest saveAccountRequest);
    AccountDto getAccount(Long accountId) throws InterruptedException, ExecutionException, JsonProcessingException;
}
