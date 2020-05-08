package com.adrixus.demo.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "currency_code")
    private String currencyCode;

    @Column(name = "country_code")
    private String countryCode;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "account_cards", joinColumns = @JoinColumn(name = "account_id"))
    @Column(name = "card_id")
    private Set<Long> cardIds = new HashSet<>();

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

    public Set<Long> getCardIds() {
        return cardIds;
    }

    public void setCardIds(Set<Long> cardIds) {
        this.cardIds = cardIds;
    }
}
