package com.example.BTL.model.request.room;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomApprovalRequest {
    @NotNull(message = "APPROVAL_STATUS_REQUIRED")
    private String approvalStatus; // "approved" hoặc "rejected"
}
