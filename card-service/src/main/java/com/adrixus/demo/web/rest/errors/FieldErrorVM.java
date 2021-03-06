package com.adrixus.demo.web.rest.errors;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

public class FieldErrorVM implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final String objectName;

    private final String field;

    private final String message;

    public FieldErrorVM(String dto, String field, String message) {
        this.objectName = dto;
        this.field = field;
        this.message = message;
    }

    public String getObjectName() {
        return objectName;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }

}
