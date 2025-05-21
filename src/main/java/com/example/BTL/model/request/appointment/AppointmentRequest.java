package com.example.BTL.model.request.appointment;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class AppointmentRequest {
    @NotNull(message = "ROOM_ID_REQUIRED")
    private Long roomId;

    @NotNull(message = "APPOINTMENT_TIME_REQUIRED")
    @Future(message = "APPOINTMENT_TIME_MUST_BE_FUTURE")
    private LocalDateTime comeDate;

    private String note;
}
