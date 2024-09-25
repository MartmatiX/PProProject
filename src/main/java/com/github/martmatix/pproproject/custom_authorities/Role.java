package com.github.martmatix.pproproject.custom_authorities;

public enum Role {

    ADMINISTRATOR("ADMINISTRATOR"), USER("USER");

    private final String value;

    Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
