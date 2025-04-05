package com.example.BTL.enums;

import java.util.stream.Stream;

public enum UserStatus {
    ACTIVE("active"),
    INACTIVE("inactive"),
    BANNED("banned");

    private final String value;

    UserStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static UserStatus fromValue(String value) {
        return Stream.of(UserStatus.values())
                .filter(status -> status.value.equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid user status: " + value));
    }
}