package com.adrixus.demo.Listener;

import com.adrixus.demo.domain.Card;
import com.adrixus.demo.repository.CardRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Component
public class AccountListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountListener.class);

    private static final String CARD_INFO_TOPIC = "cardInfo";

    private final ObjectMapper mapper = new ObjectMapper();

    private CardRepository cardRepository;

    public AccountListener(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @KafkaListener(topics = CARD_INFO_TOPIC, groupId = "6")
    @SendTo
    public String supplyCardInfo(String cardServiceMessage) throws JsonProcessingException {
        LOGGER.info("Received message: {}", cardServiceMessage);
        Set<Long> cardIds = mapper.readValue(cardServiceMessage, new TypeReference<Map<String,Set<Long>>>(){}).get("cardIds");
        Iterable<Card> cards = cardRepository.findAllById(cardIds);
        StringBuffer response = new StringBuffer();
        cards.forEach(e -> response.append(e.getCardNumber() + ","));
        return response.toString().endsWith(",") ? response.toString().substring(0, response.length()-1) : response.toString();
    }
}
