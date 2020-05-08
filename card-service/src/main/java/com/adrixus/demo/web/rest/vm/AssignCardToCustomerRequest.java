package com.adrixus.demo.web.rest.vm;


import javax.validation.constraints.NotNull;

public class AssignCardToCustomerRequest {

    @NotNull(message = "customerId is required")
    private Long customerId;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}
