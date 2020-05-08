package com.adrixus.demo.web.rest.vm;


import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class SaveAccountRequest {

    @Pattern(regexp="[\\d]{5}", message = "accountNumber is not valid")
    private String accountNumber;

    @Size(min = 3, max = 3, message = "currencyCode is not valid")
    private String currencyCode;

    @Size(min = 3, max = 3, message = "countryCode is not valid")
    private String countryCode;

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
}
