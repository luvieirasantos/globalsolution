package com.fiap.globalsolution.exception;

public class DuplicateResourceException extends RuntimeException {

    private String resource;
    private String field;
    private String value;

    public DuplicateResourceException(String resource, String field, String value) {
        super(String.format("%s com %s '%s' já existe no sistema.", resource, field, value));
        this.resource = resource;
        this.field = field;
        this.value = value;
    }

    public String getResource() {
        return resource;
    }

    public String getField() {
        return field;
    }

    public String getValue() {
        return value;
    }
}