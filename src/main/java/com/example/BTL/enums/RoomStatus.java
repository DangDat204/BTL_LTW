package com.example.BTL.enums;

import java.util.stream.Stream;

public enum RoomStatus {
    AVAILABLE("available"),
    RENTED("rented");

    private final String value;

    RoomStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static RoomStatus fromValue(String value) {
        return Stream.of(RoomStatus.values())
                .filter(status -> status.value.equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid room status: " + value));
    }
}
