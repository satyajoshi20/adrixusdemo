package com.adrixus.demo.service;

import com.adrixus.demo.Exception.AccountDoesNotExistException;
import com.adrixus.demo.domain.Account;
import com.adrixus.demo.repository.AccountRepository;
import com.adrixus.demo.web.rest.vm.AccountDto;
import com.adrixus.demo.web.rest.vm.SaveAccountRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutionException;

@Service
public class AccountServiceImpl implements AccountService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountServiceImpl.class);

    private final AccountRepository accountRepository;

    private final CardService cardService;

    public AccountServiceImpl(AccountRepository accountRepository, CardService cardService) {
        this.accountRepository = accountRepository;
        this.cardService = cardService;
    }

    @Override
    public Long saveAccount(SaveAccountRequest saveAccountRequest)
    {
        Account account = new Account();
        account.setAccountNumber(saveAccountRequest.getAccountNumber());
        account.setCurrencyCode(saveAccountRequest.getCurrencyCode());
        account.setCountryCode(saveAccountRequest.getCountryCode());
        return accountRepository.save(account).getId();
    }

    public AccountDto getAccount(Long accountId) throws InterruptedException, ExecutionException, JsonProcessingException {
        Optional<Account> accountOptional = accountRepository.findById(accountId);
        if(!accountOptional.isPresent())
        {
            throw new AccountDoesNotExistException(accountId);
        }
        Account account = accountOptional.get();
        AccountDto accountDto = new AccountDto(account);

        if(!account.getCardIds().isEmpty())
        {
            Set<Long> cardIds = account.getCardIds();
            List<String> cardNumbers = cardService.getCardInfo(cardIds);
            accountDto.setCardNumbers(cardNumbers);
        }

        return accountDto;
    }
}
