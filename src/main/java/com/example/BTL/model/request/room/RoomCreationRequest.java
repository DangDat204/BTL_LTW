package com.example.BTL.model.request.room;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomCreationRequest {
    @NotNull(message = "ADDRESS_REQUIRED")
    @Size(min = 5, max = 255, message = "ADDRESS_INVALID")
    private String address;

    @Positive(message = "AREA_MUST_BE_POSITIVE")
    private Double area;

    @Positive(message = "CAPACITY_MUST_BE_POSITIVE")
    private Long capacity;

    @Size(max = 500, message = "DESCRIPTION_TOO_LONG")
    private String description;

    @NotNull(message = "PRICE_REQUIRED")
    @Positive(message = "PRICE_MUST_BE_POSITIVE")
    private Double price;

    private String roomType; // "studio", "apartment", "shared"

    private String amenities;
}
