package com.example.BTL.controller;

import com.example.BTL.model.ApiResponse;
import com.example.BTL.model.request.appointment.AppointmentApprovalRequest;
import com.example.BTL.model.request.appointment.AppointmentRequest;
import com.example.BTL.model.response.appointment.AppointmentResponse;
import com.example.BTL.service.interfaces.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@Controller
@RequestMapping("/appointments")
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;

    @PostMapping("/createAppointment")
    @PreAuthorize("hasRole('TENANT')")
    @ResponseBody
    public ApiResponse<AppointmentResponse> createAppointment(
            @Valid @RequestBody AppointmentRequest request) {
        AppointmentResponse response = appointmentService.createAppointment(request);
        ApiResponse<AppointmentResponse> apiResponse = new ApiResponse<>(1000, "Appointment created successfully", response);
        return apiResponse;
    }

//    lay danh sach lich hen Pending
    @GetMapping("/TenantAppointmentPending")
    @PreAuthorize("hasRole('TENANT')")
    @ResponseBody
    public ApiResponse<Map<String, Object>> getTenantAppointmentPending(@RequestParam(defaultValue = "1") int pageNumber,
                                                                        @RequestParam(defaultValue = "5") int pageSize){
        Page<AppointmentResponse> appointmentPage = appointmentService.getTenantAppointmentPending(pageNumber, pageSize);
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("Tenant Pending Appointments", appointmentPage.getContent());
        response.put("currentPage", appointmentPage.getNumber() + 1);
        response.put("totalItems", appointmentPage.getTotalElements());
        response.put("totalPages", appointmentPage.getTotalPages());
        ApiResponse<Map<String, Object>> apiResponse = new ApiResponse<>(1000, "Tenant appointment is on pending", response);
        return apiResponse;
    }
    //    lay danh sach lich hen confirmed
    @GetMapping("/TenantAppointmentConfirmed")
    @PreAuthorize("hasRole('TENANT')")
    @ResponseBody
    public ApiResponse<Map<String, Object>> getTenantAppointmentConfirmed(@RequestParam(defaultValue = "1") int pageNumber,
                                                                        @RequestParam(defaultValue = "5") int pageSize){
        Page<AppointmentResponse> appointmentPage = appointmentService.getTenantAppointmentConfirmed(pageNumber, pageSize);
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("Tenant Confirmed Appointments", appointmentPage.getContent());
        response.put("currentPage", appointmentPage.getNumber() + 1);
        response.put("totalItems", appointmentPage.getTotalElements());
        response.put("totalPages", appointmentPage.getTotalPages());
        ApiResponse<Map<String, Object>> apiResponse = new ApiResponse<>(1000, "Tenant appointment confirmed", response);
        return apiResponse;
    }


// xac nhan lich hen
    @PutMapping("/approval/{id}")
    @PreAuthorize("hasRole('LANDLORD')")
    @ResponseBody
    public ApiResponse<AppointmentResponse> approveAppointment(
            @PathVariable Long id,
            @Valid @RequestBody AppointmentApprovalRequest request) {
        AppointmentResponse response = appointmentService.approveAppointment(id, request);
        ApiResponse<AppointmentResponse> apiResponse = new ApiResponse<>(1000, "Appointment status updated successfully", response);
        return apiResponse;
    }
// danh sach my appointment landlord can duyet
    @GetMapping("/pendingAppointments")
    @PreAuthorize("hasRole('LANDLORD')")
    @ResponseBody
    public ApiResponse<Map<String, Object>> getPendingAppointments(@RequestParam(defaultValue = "1") int pageNumber,
                                                            @RequestParam(defaultValue = "5") int pageSize){
        Page<AppointmentResponse> pendingAppointments = appointmentService.getPendingAppointmentsForLandlord(pageNumber, pageSize);
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("Pending rooms", pendingAppointments.getContent());
        response.put("currentPage", pendingAppointments.getNumber() + 1);
        response.put("totalItems", pendingAppointments.getTotalElements());
        response.put("totalPages", pendingAppointments.getTotalPages());

        ApiResponse<Map<String, Object>> apiResponse = new ApiResponse<>(1000, "All Pending Appointments", response);
        return apiResponse;
    }
    // danh sach my appointment landlord da duoc duyet
    @GetMapping("/confirmedAppointments")
    @PreAuthorize("hasRole('LANDLORD')")
    @ResponseBody
    public ApiResponse<Map<String, Object>> getConfirmedAppointments(@RequestParam(defaultValue = "1") int pageNumber,
                                                                   @RequestParam(defaultValue = "5") int pageSize){
        Page<AppointmentResponse> pendingAppointments = appointmentService.getConfirmedAppointmentsForLandlord(pageNumber, pageSize);
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("Confirmed rooms", pendingAppointments.getContent());
        response.put("currentPage", pendingAppointments.getNumber() + 1);
        response.put("totalItems", pendingAppointments.getTotalElements());
        response.put("totalPages", pendingAppointments.getTotalPages());

        ApiResponse<Map<String, Object>> apiResponse = new ApiResponse<>(1000, "All Confirmed Appointments", response);
        return apiResponse;
    }
}
