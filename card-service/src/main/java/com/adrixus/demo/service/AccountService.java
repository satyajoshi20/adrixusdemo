package com.adrixus.demo.service;


import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.concurrent.ExecutionException;

public interface AccountService {
    Boolean assignCardToAccount(Long cardId, Long accountId) throws ExecutionException, InterruptedException, JsonProcessingException;
}
