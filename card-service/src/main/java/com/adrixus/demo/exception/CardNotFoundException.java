package com.adrixus.demo.exception;


public class CardNotFoundException extends RuntimeException{

    private Long cardId;

    public CardNotFoundException(Long cardId)
    {
        super("Card with id " + cardId + " not found");
        this.cardId = cardId;
    }

    public Long getCardId() {
        return cardId;
    }
}
