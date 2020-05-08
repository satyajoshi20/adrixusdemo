package com.adrixus.demo.web.rest.vm;


import com.adrixus.demo.web.rest.errors.FieldErrorVM;

import java.util.ArrayList;
import java.util.List;

public class ApiResponse<T> {

    private ApiResponseStatus status;

    private String message;

    private T data;

    private List<FieldErrorVM> errors = new ArrayList<>();

    public ApiResponse(ApiResponseStatus status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public ApiResponse(ApiResponseStatus status, String message, T data, List<FieldErrorVM> errors) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.errors = errors;
    }

    public ApiResponseStatus getStatus() {
        return status;
    }

    public void setStatus(ApiResponseStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public List<FieldErrorVM> getErrors() {
        return errors;
    }

    public void setErrors(List<FieldErrorVM> errors) {
        this.errors = errors;
    }
}
