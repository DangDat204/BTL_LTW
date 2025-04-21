package com.example.BTL.model.response.room;

import com.example.BTL.model.response.Image.ImageResponse;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class RoomCreationResponse {
    private Long id;
    private String address;
    private Double area;
    private Long capacity;
    private String description;
    private String approvalStatus;
    private Double price;
    private String roomType;
    private String status;
    private String amenities;
    private List<ImageResponse> images;
    private String landlordTel; // Số điện thoại của landlord
    private String landlordEmail;
}
