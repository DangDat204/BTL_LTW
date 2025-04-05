package com.example.BTL.entity;
import com.example.BTL.enums.RoomApprovalStatus;
import com.example.BTL.enums.RoomStatus;
import com.example.BTL.enums.RoomType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "room")
@Getter
@Setter
public class Room extends BaseEntity {
    @Column(length = 255, nullable = false)
    private String address;

    @Column(precision = 10)
    private Double area;

    private Long capacity;

    @Column(length = 500)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "is_approval", nullable = false)
    private RoomApprovalStatus approvalStatus = RoomApprovalStatus.PENDING;

    @Column(precision = 10, nullable = false)
    private Double price;

    @Enumerated(EnumType.STRING)
    @Column(name = "room_type")
    private RoomType roomType;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoomStatus status = RoomStatus.AVAILABLE;

    @Column(length = 500)
    private String amenities;

    private Double latitude;

    private Double longitude;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<Image> images;

    // Getters, Setters
}