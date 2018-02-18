package com.buzzshelter.Model;

/**
 * Created by user on 17/02/2018.
 */
//Enums for Account Types

public enum AccountType {
    ADMIN("AD"),
    USER("US");

    private final String innerCode;

    AccountType(String innerCode) {
        this.innerCode = innerCode;
    }

    public String toString() {
        return innerCode;
    }
}
