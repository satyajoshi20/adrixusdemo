package com.adrixus.demo.Exception;


public class AccountDoesNotExistException extends RuntimeException {

    private Long accountId;

    public AccountDoesNotExistException(Long accountId)
    {
        super("Account with id " + accountId + " not found");
        this.accountId = accountId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }
}
