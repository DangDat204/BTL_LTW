package com.example.BTL.enums;

import java.util.stream.Stream;

public enum RoomType {
    STUDIO("studio"),
    APARTMENT("apartment"),
    SHARED("shared");

    private final String value;

    RoomType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static RoomType fromValue(String value) {
        return Stream.of(RoomType.values())
                .filter(type -> type.value.equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid room type: " + value));
    }
}
