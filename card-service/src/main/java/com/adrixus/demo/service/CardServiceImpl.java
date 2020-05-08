package com.adrixus.demo.service;

import com.adrixus.demo.domain.Card;
import com.adrixus.demo.exception.AccountDoesNotExistException;
import com.adrixus.demo.exception.CardNotFoundException;
import com.adrixus.demo.exception.CustomerNotFoundException;
import com.adrixus.demo.repository.CardRepository;
import com.adrixus.demo.web.rest.vm.SaveCardRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Service
public class CardServiceImpl implements CardService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CardServiceImpl.class);

    private final CardRepository cardRepository;

    private final CustomerService customerService;

    private final AccountService accountService;

    public CardServiceImpl(CardRepository cardRepository, CustomerService customerService, AccountService accountService) {
        this.cardRepository = cardRepository;
        this.customerService = customerService;
        this.accountService = accountService;
    }

    /**
     * save new card to db
     * @param saveCardRequest
     * @return
     */
    @Override
    public Long saveCard(SaveCardRequest saveCardRequest)
    {
        Card card = new Card();
        card.setCardNumber(saveCardRequest.getCardNumber());
        card.setCardType(saveCardRequest.getCardType().name());
        card.setCvc(saveCardRequest.getCvc());
        card.setPassword(saveCardRequest.getPassword());
        return cardRepository.save(card).getId();
    }


    /**
     * Validate cardId, customerId and assign card to customer
     * @param cardId
     * @param customerId
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Override
    public void assignCardToCustomer(Long cardId, Long customerId) throws ExecutionException, InterruptedException {
        Optional<Card> cardOptional = cardRepository.findById(cardId);
        if(!cardOptional.isPresent())
        {
            throw new CardNotFoundException(cardId);
        }

        if(!customerService.verifyCustomerId(customerId))
        {
            throw new CustomerNotFoundException(customerId);
        }

        Card card = cardOptional.get();
        card.setCustomerId(customerId);
        cardRepository.save(card);
    }

    /**
     * Validate cardId, accountId and assign card to account
     * @param cardId
     * @param accountId
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws JsonProcessingException
     */
    @Override
    public void assignCardToAccount(Long cardId, Long accountId) throws InterruptedException, ExecutionException, JsonProcessingException {
        Optional<Card> cardOptional = cardRepository.findById(cardId);
        if(!cardOptional.isPresent())
        {
            throw new CardNotFoundException(cardId);
        }

        if(!accountService.assignCardToAccount(cardId, accountId))
        {
            throw new AccountDoesNotExistException(accountId);
        }

        Card card = cardOptional.get();
        card.setAccountId(accountId);
        cardRepository.save(card);
    }
}
