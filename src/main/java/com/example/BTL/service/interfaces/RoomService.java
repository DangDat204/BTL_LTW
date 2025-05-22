package com.example.BTL.service.interfaces;

import com.example.BTL.enums.RoomType;
import com.example.BTL.model.request.room.RoomCreationRequest;
import com.example.BTL.model.request.room.RoomApprovalRequest;
import com.example.BTL.model.response.room.RoomCreationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RoomService {
//    landlord
    RoomCreationResponse createRoom(RoomCreationRequest request);
//    List<RoomCreationResponse> getAllRooms();
    Page<RoomCreationResponse> getAllRooms(int pageNumber, int pageSize);
    RoomCreationResponse getRoomById(Long id);
    RoomCreationResponse updateRoom(Long roomId, RoomCreationRequest request);
    void deleteRoom(Long roomId);
    void restoreRoom(Long roomId);
//    List<RoomCreationResponse> getMyRoomsPending();
    Page<RoomCreationResponse> getMyRoomsPending(int pageNumber, int pageSize);
//    List<RoomCreationResponse> getMyRoomsApproved();
    Page<RoomCreationResponse> getMyRoomsApproved(int pageNumber, int pageSize);
//    admin
    RoomCreationResponse approveRoom(Long roomId, RoomApprovalRequest request);
    Page<RoomCreationResponse> getPendingRooms(int pageNumber, int pageSize);

// all
    Page<RoomCreationResponse> filterRooms(Double minPrice, Double maxPrice,
                                           Double minArea, Double maxArea,
                                           String address, RoomType roomType,
                                           int pageNumber, int pageSize);

}
