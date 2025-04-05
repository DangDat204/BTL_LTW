package com.example.BTL.enums;


import java.util.stream.Stream;

public enum RoleEnum {
    TENANT("tenant"),
    LANDLORD("landlord"),
    ADMIN("admin");

    private final String value;

    RoleEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static RoleEnum fromValue(String value) {
        return Stream.of(RoleEnum.values())
                .filter(role -> role.value.equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid role: " + value));
    }
}
