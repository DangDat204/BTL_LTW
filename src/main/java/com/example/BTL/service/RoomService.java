package com.example.BTL.service;

import com.example.BTL.model.request.room.RoomCreationRequest;
import com.example.BTL.model.response.room.RoomCreationResponse;

public interface RoomService {
    RoomCreationResponse createRoom(RoomCreationRequest request);
}
