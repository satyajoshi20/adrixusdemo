package com.adrixus.demo.web.rest.vm;


import javax.validation.constraints.NotNull;

public class AssignCardToAccountRequest {

    @NotNull(message = "accountId is required")
    private Long accountId;

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }
}
