package com.adrixus.demo.web.rest.vm;


import com.adrixus.demo.domain.Account;

import java.util.List;

public class AccountDto {

    private Long id;

    private String accountNumber;

    private String currencyCode;

    private String countryCode;

    private List<String> cardNumbers;

    public AccountDto() {
    }

    public AccountDto(Account account)
    {
        this.id = account.getId();
        this.accountNumber = account.getAccountNumber();
        this.currencyCode = account.getCurrencyCode();
        this.countryCode = account.getCountryCode();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public List<String> getCardNumbers() {
        return cardNumbers;
    }

    public void setCardNumbers(List<String> cardNumbers) {
        this.cardNumbers = cardNumbers;
    }
}
