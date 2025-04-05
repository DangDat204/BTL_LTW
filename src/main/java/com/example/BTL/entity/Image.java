package com.example.BTL.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "image")
@Getter
@Setter
public class Image extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @Column(length = 255, nullable = false)
    private String url;

    @Column(name = "is_primary")
    private Boolean isPrimary = false;

    // Getters, Setters
}