package com.example.BTL.service.interfaces;

import com.example.BTL.model.request.appointment.AppointmentApprovalRequest;
import com.example.BTL.model.request.appointment.AppointmentRequest;
import com.example.BTL.model.response.appointment.AppointmentResponse;
import org.springframework.data.domain.Page;

public interface AppointmentService {
//    tenent
    AppointmentResponse createAppointment(AppointmentRequest request);
    Page<AppointmentResponse> getTenantAppointmentPending(int pageNumber, int pageSize);
    Page<AppointmentResponse> getTenantAppointmentConfirmed(int pageNumber, int pageSize);
//    landlord
    AppointmentResponse approveAppointment(Long appointmentId, AppointmentApprovalRequest request);
    Page<AppointmentResponse> getPendingAppointmentsForLandlord(int pageNumber, int pageSize);
    Page<AppointmentResponse> getConfirmedAppointmentsForLandlord(int pageNumber, int pageSize);
}
