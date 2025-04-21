package com.example.BTL.controller;

import com.example.BTL.model.ApiResponse;
import com.example.BTL.model.request.Image.ImageUploadRequest;
import com.example.BTL.model.request.room.RoomCreationRequest;
import com.example.BTL.model.response.Image.ImageResponse;
import com.example.BTL.model.response.room.RoomCreationResponse;
import com.example.BTL.service.interfaces.ImageService;
import com.example.BTL.service.interfaces.RoomService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/rooms")
public class RoomController {
    @Autowired
    private RoomService roomService;
    @Autowired
    private ImageService imageService;

    @PreAuthorize("hasRole('LANDLORD')")
    @GetMapping("/addRoom")
    public String addRoom(){
        System.out.println("oke");
        System.out.println("oke2");
        return "landlord/addRoom.html";
    }
// tao phong
    @PreAuthorize("hasRole('LANDLORD')")
    @PostMapping("/create_room")
    @ResponseBody
    public ApiResponse<RoomCreationResponse> createRoom(@Valid @RequestBody RoomCreationRequest request) {
        RoomCreationResponse apiresponse = roomService.createRoom(request);
        return ApiResponse.<RoomCreationResponse>builder()
                .result(apiresponse)
                .message("Room created successfully")
                .build();
    }
    // Sửa phòng
    @PutMapping("/updateRoom/{id}")
    @PreAuthorize("hasRole('LANDLORD')")
    @ResponseBody
    public ApiResponse<RoomCreationResponse> updateRoom(
            @PathVariable Long id,
            @Valid @RequestBody RoomCreationRequest request) {
        RoomCreationResponse response = roomService.updateRoom(id, request);
        ApiResponse<RoomCreationResponse> apiResponse = new ApiResponse<>(1000, "Room updated successfully", response);
        return apiResponse;
    }
// Lay danh sach phong, all roles
    @GetMapping("/getAllRooms")
    @ResponseBody
    public ApiResponse<List<RoomCreationResponse>> getAllRooms() {
        List<RoomCreationResponse> rooms = roomService.getAllRooms();
        ApiResponse<List<RoomCreationResponse>> apiResponse = new ApiResponse<>(1000, "All rooms", rooms);
        return apiResponse;
    }

    // Lấy chi tiết phòng, all roles
    @GetMapping("/{id}")
    @ResponseBody
    public ApiResponse<RoomCreationResponse> getRoomById(@PathVariable Long id) {
        RoomCreationResponse response = roomService.getRoomById(id);
        ApiResponse<RoomCreationResponse> apiResponse = new ApiResponse<>(1000, "Get room by Id", response);
        return apiResponse;
    }

    @PostMapping("/{id}/images")
    @PreAuthorize("hasRole('LANDLORD')")
    @ResponseBody
    public ApiResponse<List<ImageResponse>> uploadImages(
            @PathVariable Long id,
            @Valid @RequestBody List<ImageUploadRequest> requests) {
        List<ImageResponse> responses = imageService.uploadImages(id, requests);
        return ApiResponse.<List<ImageResponse>>builder()
                .result(responses)
                .message("Images uploaded successfully")
                .build();
    }




}
