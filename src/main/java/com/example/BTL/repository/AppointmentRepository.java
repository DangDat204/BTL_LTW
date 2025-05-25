package com.example.BTL.repository;

import com.example.BTL.entity.Appointment;
import com.example.BTL.enums.AppointmentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
//    tenant
    Page<Appointment> findByTenantIdAndStatus(Long tenantId, AppointmentStatus appointmentStatus, Pageable pageable);
//    landlord
// Phương thức mới: Lấy danh sách lịch hẹn PENDING của các phòng thuộc landlord
    @Query("SELECT a FROM Appointment a WHERE a.room.user.id = :landlordId AND a.status = :status")
    Page<Appointment> findByRoomUserIdAndStatus(Long landlordId, AppointmentStatus status, Pageable pageable);


}
