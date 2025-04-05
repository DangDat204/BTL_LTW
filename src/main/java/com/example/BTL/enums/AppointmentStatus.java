package com.example.BTL.enums;

import java.util.stream.Stream;

public enum AppointmentStatus {
    PENDING("pending"),
    CONFIRMED("confirmed"),
    REJECTED("rejected"),
    COMPLETED("completed");

    private final String value;

    AppointmentStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static AppointmentStatus fromValue(String value) {
        return Stream.of(AppointmentStatus.values())
                .filter(status -> status.value.equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid appointment status: " + value));
    }
}
