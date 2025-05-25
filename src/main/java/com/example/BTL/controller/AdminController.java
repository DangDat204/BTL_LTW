package com.example.BTL.controller;

import com.example.BTL.model.ApiResponse;
import com.example.BTL.model.request.room.RoomApprovalRequest;
import com.example.BTL.model.response.room.RoomCreationResponse;
import com.example.BTL.model.response.user.RegisterResponse;
import com.example.BTL.repository.UserRepository;
import com.example.BTL.service.interfaces.RoomService;
import com.example.BTL.service.interfaces.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private RoomService roomService;
    @Autowired
    private UserService userService;

    @PutMapping("/approval/{id}")
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

    @GetMapping("/pendingRooms")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Map<String, Object>> getPendingRooms(@RequestParam(defaultValue = "1") int pageNumber,
                                                                   @RequestParam(defaultValue = "5") int pageSize){
        Page<RoomCreationResponse> pendingRooms = roomService.getPendingRooms(pageNumber, pageSize);
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("Pending rooms", pendingRooms.getContent());
        response.put("currentPage", pendingRooms.getNumber() + 1);
        response.put("totalItems", pendingRooms.getTotalElements());
        response.put("totalPages", pendingRooms.getTotalPages());

        ApiResponse<Map<String, Object>> apiResponse = new ApiResponse<>(1000, "All Pending rooms", response);
        return apiResponse;
    }

    //    admin
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getAllUsers")
    @ResponseBody
    public ApiResponse<Map<String, Object>> getAllUsers(@RequestParam(defaultValue = "1") int pageNumber,
                                                        @RequestParam(defaultValue = "5") int pageSize){
//        Pageable pageable = Pageable.ofSize(pageSize).withPage(pageNumber - 1);
        Page<RegisterResponse> userPage = userService.getAllUsers(pageNumber, pageSize);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("All user", userPage.getContent());
        response.put("currentPage", userPage.getNumber() + 1);
        response.put("totalItems", userPage.getTotalElements());
        response.put("totalPages", userPage.getTotalPages());

        ApiResponse<Map<String, Object>> apiResponse = new ApiResponse<>(1000, "all Users", response);
        return apiResponse;
    }
}
