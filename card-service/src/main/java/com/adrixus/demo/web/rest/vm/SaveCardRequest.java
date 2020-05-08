package com.adrixus.demo.web.rest.vm;


import com.adrixus.demo.web.rest.enumeration.CardType;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

public class SaveCardRequest {

    @Pattern(regexp="[\\d]{16}", message = "cardNumber is not valid")
    private String cardNumber;

    private CardType cardType;

    @Min(value = 100, message = "value must be between 100 and 999")
    @Max(value = 999, message = "value must be between 100 and 999")
    private Integer cvc;

    private String password;

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public Integer getCvc() {
        return cvc;
    }

    public void setCvc(Integer cvc) {
        this.cvc = cvc;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
