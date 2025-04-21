package com.example.BTL.controller;

import com.example.BTL.model.ApiResponse;
import com.example.BTL.model.request.room.RoomApprovalRequest;
import com.example.BTL.model.response.room.RoomCreationResponse;
import com.example.BTL.service.interfaces.RoomService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private RoomService roomService;
    @PutMapping("/rooms/{id}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<RoomCreationResponse> approveRoom(
            @PathVariable Long id,
            @Valid @RequestBody RoomApprovalRequest request) {
        RoomCreationResponse response = roomService.approveRoom(id, request);
        return ApiResponse.<RoomCreationResponse>builder()
                .result(response)
                .message("Room approval updated")
                .build();
    }
}
