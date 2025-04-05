package com.example.BTL.model.response.room;

import com.example.BTL.model.request.Image.ImageCreationRequest;
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

}
