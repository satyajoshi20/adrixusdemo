package com.adrixus.demo.web.rest.vm;


public class SaveAccountResponse {

    private Long accountId;

    public SaveAccountResponse(Long accountId) {
        this.accountId = accountId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }
}
