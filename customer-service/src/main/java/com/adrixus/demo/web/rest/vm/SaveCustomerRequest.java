package com.adrixus.demo.web.rest.vm;


import javax.validation.constraints.NotBlank;

public class SaveCustomerRequest {

    @NotBlank(message = "Name must be a valid value")
    private String name;

    @NotBlank(message = "addressLine1 must be a valid value")
    private String addressLine1;

    private String addressLine2;

    @NotBlank(message = "state must be a valid value")
    private String state;

    @NotBlank(message = "city must be a valid value")
    private String city;

    @NotBlank(message = "country must be a valid value")
    private String country;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
