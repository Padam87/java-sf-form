package com.symfony.form;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

/**
 * A value object compatible with the format the FOSRestBundle returns when a form is invalid
 */
public class ValidationErrorModel {
    @SerializedName("code")
    private Integer code;
    @SerializedName("message")
    private String message;
    @SerializedName("errors")
    private FormErrorModel errors;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public FormErrorModel getErrors() {
        return errors;
    }

    public void setErrors(FormErrorModel errors) {
        this.errors = errors;
    }
}
