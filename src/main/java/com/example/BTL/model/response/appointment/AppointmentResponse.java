package com.example.BTL.model.response.appointment;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AppointmentResponse {
    private Long id;
    private Long roomId;
    private Long tenantId;
    private LocalDateTime comeDate;
    private String status;
    private String note;
}
