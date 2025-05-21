package com.example.BTL.repository;

import com.example.BTL.entity.Room;
import com.example.BTL.enums.RoomApprovalStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
//    List<Room> findByApprovalStatusAndDeletedFalse(RoomApprovalStatus approvalStatus);
    Page<Room> findByApprovalStatusAndDeletedFalse(RoomApprovalStatus approvalStatus, Pageable pageable);
    Optional<Room> findByIdAndDeletedFalse(Long id);
    Optional<Room> findByIdAndDeletedTrue(Long id);
    Page<Room> findByUserIdAndApprovalStatusAndDeletedFalse(Long userId, RoomApprovalStatus approvalStatus, Pageable pageable);
}
