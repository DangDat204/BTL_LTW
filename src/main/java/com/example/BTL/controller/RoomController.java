package com.example.BTL.controller;

import com.example.BTL.enums.RoomType;
import com.example.BTL.model.ApiResponse;
import com.example.BTL.model.request.Image.ImageUploadRequest;
import com.example.BTL.model.request.room.RoomCreationRequest;
import com.example.BTL.model.response.Image.ImageResponse;
import com.example.BTL.model.response.room.RoomCreationResponse;
import com.example.BTL.service.interfaces.ImageService;
import com.example.BTL.service.interfaces.RoomService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/rooms")
public class RoomController {
    @Autowired
    private RoomService roomService;
    @Autowired
    private ImageService imageService;

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


    // upload anh cho phong
    @PostMapping("/{roomId}/images")
    @PreAuthorize("hasRole('LANDLORD')")
    @ResponseBody
    public ApiResponse<List<ImageResponse>> uploadImages(
            @PathVariable Long roomId,
            @RequestParam("images") MultipartFile[] images) {
        List<ImageResponse> responses = imageService.uploadRoomImages(roomId, images);
        return ApiResponse.<List<ImageResponse>>builder()
                .result(responses)
                .message("Images uploaded successfully")
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

    // Xóa phòng (xóa mềm)
    @DeleteMapping("/deleteRoom/{id}")
    @PreAuthorize("hasRole('LANDLORD')")
    @ResponseBody
    public ApiResponse<Void> deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
        ApiResponse<Void> apiResponse = new ApiResponse<>(1000, "Room deleted successfully", null);
        return apiResponse;
    }

    // khoi phuc phong
    @PatchMapping("/restoreRoom/{id}")
    @PreAuthorize("hasRole('LANDLORD')")
    @ResponseBody
    public ApiResponse<Void> restoreRoom(@PathVariable Long id) {
        roomService.restoreRoom(id);
        ApiResponse<Void> apiResponse = new ApiResponse<>(1000, "Room restored successfully", null);
        return apiResponse;
    }

    // Lấy danh sách phòng cua landlord chưa được duyệt
    @GetMapping("/MyRoomsPending")
    @PreAuthorize("hasRole('LANDLORD')")
    @ResponseBody
    public ApiResponse<Map<String, Object>> getMyRoomsPending(@RequestParam(defaultValue = "1") int pageNumber,
            @RequestParam(defaultValue = "5") int pageSize) {
        Page<RoomCreationResponse> roomPage = roomService.getMyRoomsPending(pageNumber, pageSize);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("My Pending Rooms", roomPage.getContent());
        response.put("currentPage", roomPage.getNumber() + 1);
        response.put("totalItems", roomPage.getTotalElements());
        response.put("totalPages", roomPage.getTotalPages());

        ApiResponse<Map<String, Object>> apiResponse = new ApiResponse<>(1000, "My room is on pending", response);
        return apiResponse;
    }

    // Lấy danh sách phòng cua landlord da được duyệt
    @GetMapping("/MyRoomsApproved")
    @PreAuthorize("hasRole('LANDLORD')")
    @ResponseBody
    public ApiResponse<Map<String, Object>> getMyRoomsApproved(@RequestParam(defaultValue = "1") int pageNumber,
            @RequestParam(defaultValue = "5") int pageSize) {
        Page<RoomCreationResponse> roomPage = roomService.getMyRoomsApproved(pageNumber, pageSize);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("My Approved Rooms", roomPage.getContent());
        response.put("currentPage", roomPage.getNumber() + 1);
        response.put("totalItems", roomPage.getTotalElements());
        response.put("totalPages", roomPage.getTotalPages());

        ApiResponse<Map<String, Object>> apiResponse = new ApiResponse<>(1000, "My room is approved", response);
        return apiResponse;
    }

    // Lấy danh sách phòng cua landlord da xoa
    @GetMapping("/MyRoomsDeleted")
    @PreAuthorize("hasRole('LANDLORD')")
    @ResponseBody
    public ApiResponse<Map<String, Object>> getMyRoomsDeleted(@RequestParam(defaultValue = "1") int pageNumber,
                                                               @RequestParam(defaultValue = "5") int pageSize) {
        Page<RoomCreationResponse> roomPage = roomService.getMyDeletedRoom(pageNumber, pageSize);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("My Deleted Rooms", roomPage.getContent());
        response.put("currentPage", roomPage.getNumber() + 1);
        response.put("totalItems", roomPage.getTotalElements());
        response.put("totalPages", roomPage.getTotalPages());

        ApiResponse<Map<String, Object>> apiResponse = new ApiResponse<>(1000, "My room is deleted", response);
        return apiResponse;
    }

    

    // Lay danh sach phong, all roles
    @GetMapping("/getAllRooms")
    @ResponseBody
    public ApiResponse<Map<String, Object>> getAllRooms(@RequestParam(defaultValue = "1") int pageNumber,
            @RequestParam(defaultValue = "5") int pageSize) {
        // List<RoomCreationResponse> rooms = roomService.getAllRooms();
        Page<RoomCreationResponse> roomPage = roomService.getAllRooms(pageNumber, pageSize);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("rooms", roomPage.getContent());
        response.put("currentPage", roomPage.getNumber() + 1);
        response.put("totalItems", roomPage.getTotalElements());
        response.put("totalPages", roomPage.getTotalPages());

        ApiResponse<Map<String, Object>> apiResponse = new ApiResponse<>(1000, "All Paged rooms", response);
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

    // loc phong (filterRooms) all Roles
    @GetMapping("/filterRooms")
    @ResponseBody
    public ApiResponse<Map<String, Object>> filterRooms(
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Double minArea,
            @RequestParam(required = false) Double maxArea,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) RoomType roomType,
            @RequestParam(defaultValue = "1") int pageNumber,
            @RequestParam(defaultValue = "5") int pageSize) {
        Page<RoomCreationResponse> roomPage = roomService.filterRooms(minPrice, maxPrice, minArea, maxArea, address,
                roomType, pageNumber, pageSize);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("FilterRooms", roomPage.getContent());
        response.put("currentPage", roomPage.getNumber() + 1);
        response.put("totalItems", roomPage.getTotalElements());
        response.put("totalPages", roomPage.getTotalPages());

        ApiResponse<Map<String, Object>> apiResponse = new ApiResponse<>(1000, "All Paged FilterRooms", response);
        return apiResponse;

    }

}
