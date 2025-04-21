package com.example.BTL.repository;

import com.example.BTL.entity.Room;
import com.example.BTL.enums.RoomApprovalStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findByApprovalStatusAndDeletedFalse(RoomApprovalStatus approvalStatus);
    Optional<Room> findByIdAnDeleteFalse(Long id);
}
