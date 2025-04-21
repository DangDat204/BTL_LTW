package com.example.BTL.service.interfaces;

import com.example.BTL.model.request.room.RoomCreationRequest;
import com.example.BTL.model.request.room.RoomApprovalRequest;
import com.example.BTL.model.response.room.RoomCreationResponse;

import java.util.List;

public interface RoomService {
    RoomCreationResponse createRoom(RoomCreationRequest request);
    RoomCreationResponse approveRoom(Long roomId, RoomApprovalRequest request);
    List<RoomCreationResponse> getAllRooms();
    RoomCreationResponse getRoomById(Long id);
    RoomCreationResponse updateRoom(Long roomId, RoomCreationRequest request);
    void deleteRoom(Long roomId);
}
