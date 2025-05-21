package com.example.BTL.model.request.appointment;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppointmentApprovalRequest {
    @NotNull(message = "STATUS_REQUIRED")
    private String status; // "confirmed" hoặc "rejected"
}
