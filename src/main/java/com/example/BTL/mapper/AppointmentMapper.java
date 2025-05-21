package com.example.BTL.mapper;

import com.example.BTL.entity.Appointment;
import com.example.BTL.model.request.appointment.AppointmentRequest;
import com.example.BTL.model.response.appointment.AppointmentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {
    @Mapping(target = "room", ignore = true)
    @Mapping(target = "tenant", ignore = true)
    Appointment toAppointment(AppointmentRequest request);

    @Mapping(source = "room.id", target = "roomId")
    @Mapping(source = "tenant.id", target = "tenantId")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "comeDate", target = "comeDate")
    AppointmentResponse toAppointmentResponse(Appointment appointment);
}
