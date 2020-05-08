package com.adrixus.demo.web.rest.vm;


public class SaveCustomerResponse {

    private Long customerId;

    public SaveCustomerResponse(Long customerId) {
        this.customerId = customerId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}
