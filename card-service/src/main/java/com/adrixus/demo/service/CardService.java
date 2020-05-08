package com.adrixus.demo.service;


import com.adrixus.demo.domain.Card;
import com.adrixus.demo.web.rest.vm.SaveCardRequest;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

public interface CardService {
    Long saveCard(SaveCardRequest saveCardRequest);
    void assignCardToCustomer(Long cardId, Long customerId) throws ExecutionException, InterruptedException;
    void assignCardToAccount(Long cardId, Long accountId) throws InterruptedException, ExecutionException, JsonProcessingException;
}
