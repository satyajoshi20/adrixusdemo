package com.adrixus.demo.service;


import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public interface CardService {

    List<String> getCardInfo(Set<Long> cardIds) throws ExecutionException, InterruptedException, JsonProcessingException;
}
