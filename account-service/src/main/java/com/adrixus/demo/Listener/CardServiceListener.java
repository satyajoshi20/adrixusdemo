package com.adrixus.demo.Listener;

import com.adrixus.demo.Listener.model.AssignCardToAccountModel;
import com.adrixus.demo.domain.Account;
import com.adrixus.demo.repository.AccountRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CardServiceListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(CardServiceListener.class);

    private static final String ASSIGN_CARD_TOPIC = "assignCard";

    private final ObjectMapper mapper = new ObjectMapper();

    private final AccountRepository accountRepository;

    public CardServiceListener(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @KafkaListener(topics = ASSIGN_CARD_TOPIC, groupId = "3")
    @SendTo
    public String assignCardToAccount(String cardServiceMessage) throws JsonProcessingException {
        LOGGER.info("Received message: {}", cardServiceMessage);
        AssignCardToAccountModel assignCardToAccountModel = mapper.readValue(cardServiceMessage, AssignCardToAccountModel.class);
        Long cardId = Long.parseLong(assignCardToAccountModel.getCardId());
        Long accountId = Long.parseLong(assignCardToAccountModel.getAccountId());

        Optional<Account> accountOptional = accountRepository.findById(accountId);
        if(!accountOptional.isPresent())
        {
            return Boolean.FALSE.toString();
        }

        Account account = accountOptional.get();
        account.getCardIds().add(cardId);
        accountRepository.save(account);
        return Boolean.TRUE.toString();
    }
}
