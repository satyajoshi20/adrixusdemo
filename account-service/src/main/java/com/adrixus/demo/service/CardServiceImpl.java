package com.adrixus.demo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
public class CardServiceImpl implements CardService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CardServiceImpl.class);

    @Value("${card.info.topic}")
    private String cardInfoTopic;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private ReplyingKafkaTemplate<Object, String, String> replyingKafkaTemplate;

    public CardServiceImpl(ReplyingKafkaTemplate<Object, String, String> replyingKafkaTemplate) {
        this.replyingKafkaTemplate = replyingKafkaTemplate;
    }

    @Override
    public List<String> getCardInfo(Set<Long> cardIds) throws ExecutionException, InterruptedException, JsonProcessingException {
        String json = getJsonForInput(cardIds);
        LOGGER.info("sending value {}", json);

        ProducerRecord<Object, String> record = new ProducerRecord<>(cardInfoTopic, null, null, json);
        RequestReplyFuture<Object, String, String> future = replyingKafkaTemplate.sendAndReceive(record);
        ConsumerRecord<Object, String> response = future.get();
        LOGGER.info("Response received - " + response.value());

        if(!response.value().trim().isEmpty())
        {
            return Arrays.asList(response.value().split(","));
        }

        return new ArrayList<>();
    }

    private String getJsonForInput(Set<Long> cardIds) throws JsonProcessingException {
        Map<String, Set<Long>> assignCardToAccountMap = new HashMap<>();
        assignCardToAccountMap.put("cardIds", cardIds);
        return objectMapper.writeValueAsString(assignCardToAccountMap);
    }
}
