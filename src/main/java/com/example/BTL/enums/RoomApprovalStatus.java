package com.example.BTL.enums;

import java.util.stream.Stream;

public enum RoomApprovalStatus {
    PENDING("pending"),
    APPROVED("approved"),
    REJECTED("rejected");

    private final String value;

    RoomApprovalStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static RoomApprovalStatus fromValue(String value) {
        return Stream.of(RoomApprovalStatus.values())
                .filter(status -> status.value.equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid room approval status: " + value));
    }
}
