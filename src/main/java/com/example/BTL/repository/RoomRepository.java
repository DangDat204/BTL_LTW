package com.example.BTL.repository;

import com.example.BTL.entity.Room;
import com.example.BTL.enums.RoomApprovalStatus;
import com.example.BTL.enums.RoomType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Query("SELECT r FROM Room r WHERE " +
            "(:minPrice IS NULL OR r.price >= :minPrice) AND " +
            "(:maxPrice IS NULL OR r.price <= :maxPrice) AND " +
            "(:minArea IS NULL OR r.area >= :minArea) AND " +
            "(:maxArea IS NULL OR r.area <= :maxArea) AND " +
            "(:address IS NULL OR LOWER(r.address) LIKE LOWER(CONCAT('%', :address, '%'))) AND " +
            "(:roomType IS NULL OR r.roomType = :roomType) AND " +
            "r.approvalStatus = 'APPROVED' AND " +
            "r.deleted = false")
    Page<Room> filterRooms(
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice,
            @Param("minArea") Double minArea,
            @Param("maxArea") Double maxArea,
            @Param("address") String address,
            @Param("roomType") RoomType roomType,
            Pageable pageable
    );
}
