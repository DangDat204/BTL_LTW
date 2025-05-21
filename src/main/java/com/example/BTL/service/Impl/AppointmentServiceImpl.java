package com.example.BTL.service.Impl;

import com.example.BTL.entity.Appointment;
import com.example.BTL.entity.Room;
import com.example.BTL.entity.User;
import com.example.BTL.enums.AppointmentStatus;
import com.example.BTL.enums.RoomApprovalStatus;
import com.example.BTL.exception.AppException;
import com.example.BTL.exception.ErrorCode;
import com.example.BTL.mapper.AppointmentMapper;
import com.example.BTL.model.request.appointment.AppointmentApprovalRequest;
import com.example.BTL.model.request.appointment.AppointmentRequest;
import com.example.BTL.model.response.appointment.AppointmentResponse;
import com.example.BTL.model.response.room.RoomCreationResponse;
import com.example.BTL.repository.AppointmentRepository;
import com.example.BTL.repository.RoomRepository;
import com.example.BTL.repository.UserRepository;
import com.example.BTL.service.interfaces.AppointmentService;
import com.example.BTL.service.interfaces.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentServiceImpl implements AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomService roomService;

    @Autowired
    private AppointmentMapper appointmentMapper;

    @Override
    @PreAuthorize("hasRole('TENANT')")
    public AppointmentResponse createAppointment(AppointmentRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User tenant = userRepository.findUserByUsername(username)
                .orElseThrow(()->new AppException(ErrorCode.USER_NOT_EXISTED));
        Room room = roomRepository.findByIdAndDeletedFalse(request.getRoomId())
                .orElseThrow(()->new AppException(ErrorCode.ROOM_NOT_FOUND));
        Appointment appointment = appointmentMapper.toAppointment(request);
        appointment.setRoom(room);
        appointment.setTenant(tenant);
        appointmentRepository.save(appointment);
        return appointmentMapper.toAppointmentResponse(appointment);
    }
//    danh sach lich hen pending
    @PreAuthorize("hasRole('TENANT')")
    @Override
    public Page<AppointmentResponse> getTenantAppointmentPending(int pageNumber, int pageSize) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User tenant = userRepository.findUserByUsername(username)
                .orElseThrow(()->new AppException(ErrorCode.USER_NOT_EXISTED));
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        Page<Appointment> appointmentPage = appointmentRepository.findByTenantIdAndStatus(tenant.getId(), AppointmentStatus.PENDING, pageable);
        List<AppointmentResponse> appointmentResponses = appointmentPage.getContent().stream()
                .map(appointmentMapper::toAppointmentResponse)
                .toList();
        return new PageImpl<>(appointmentResponses, pageable, appointmentPage.getTotalElements());
    }
//danh sach lich hen confirmed
    @PreAuthorize("hasRole('TENANT')")
    @Override
    public Page<AppointmentResponse> getTenantAppointmentConfirmed(int pageNumber, int pageSize) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User tenant = userRepository.findUserByUsername(username)
                .orElseThrow(()->new AppException(ErrorCode.USER_NOT_EXISTED));
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        Page<Appointment> appointmentPage = appointmentRepository.findByTenantIdAndStatus(tenant.getId(), AppointmentStatus.CONFIRMED, pageable);
        List<AppointmentResponse> appointmentResponses = appointmentPage.getContent().stream()
                .map(appointmentMapper::toAppointmentResponse)
                .toList();
        return new PageImpl<>(appointmentResponses, pageable, appointmentPage.getTotalElements());
    }





    // xac nhan lich hen
    @Override
    @PreAuthorize("hasRole('LANDLORD')")
    public AppointmentResponse approveAppointment(Long appointmentId, AppointmentApprovalRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User landlord = userRepository.findUserByUsername(username)
                .orElseThrow(()->new AppException(ErrorCode.USER_NOT_EXISTED));
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(()-> new AppException(ErrorCode.APPOINTMENT_NOT_FOUND));
        // Kiểm tra quyền: Chỉ landlord sở hữu phòng mới được xác nhận/từ chối
        Room room = appointment.getRoom();
        if (!room.getUser().getId().equals(landlord.getId())) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
        AppointmentStatus status = AppointmentStatus.fromValue(request.getStatus());
        appointment.setStatus(status);
        appointmentRepository.save(appointment);
        return appointmentMapper.toAppointmentResponse(appointment);
    }

    @Override
    @PreAuthorize("hasRole('LANDLORD')")
    public Page<AppointmentResponse> getPendingAppointmentsForLandlord(int pageNumber, int pageSize) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User landlord = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize); // Lưu ý pageNumber - 1
        Page<Appointment> appointmentPage = appointmentRepository.findByRoomUserIdAndStatus(landlord.getId(), AppointmentStatus.PENDING, pageable);
        List<AppointmentResponse> responses = appointmentPage.getContent().stream()
                .map(appointmentMapper::toAppointmentResponse)
                .collect(Collectors.toList());

        return new PageImpl<>(responses, pageable, appointmentPage.getTotalElements());
    }
}
