package com.adrixus.demo.web.rest.vm;


public class SaveCardResponse {

    private Long cardId;

    public SaveCardResponse(Long cardId) {
        this.cardId = cardId;
    }

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }
}
