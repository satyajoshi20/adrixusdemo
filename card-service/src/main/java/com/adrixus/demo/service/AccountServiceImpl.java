package com.adrixus.demo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class AccountServiceImpl implements AccountService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Value("${card.account.assign.topic}")
    private String cardAccountAssignTopic;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    @Qualifier("accountServiceReplyingKafkaTemplate")
    private ReplyingKafkaTemplate<Object, String, String> accountServiceReplyingKafkaTemplate;

    @Override
    public Boolean assignCardToAccount(Long cardId, Long accountId) throws ExecutionException, InterruptedException, JsonProcessingException {
        String json = getJsonForInput(cardId, accountId);
        LOGGER.info("sending value {}", json);

        ProducerRecord<Object, String> record = new ProducerRecord<>(cardAccountAssignTopic, null, null, json);
        RequestReplyFuture<Object, String, String> future = accountServiceReplyingKafkaTemplate.sendAndReceive(record);
        ConsumerRecord<Object, String> response = future.get();
        LOGGER.info("Response received - " + response.value());

        return Boolean.valueOf(response.value());
    }

    private String getJsonForInput(Long cardId, Long accountId) throws JsonProcessingException {
        Map<String, String> assignCardToAccountMap = new HashMap<>();
        assignCardToAccountMap.put("cardId", cardId.toString());
        assignCardToAccountMap.put("accountId", accountId.toString());
        return objectMapper.writeValueAsString(assignCardToAccountMap);
    }


}
