package com.example.BTL.mapper;

import com.example.BTL.entity.Room;
import com.example.BTL.model.request.room.RoomCreationRequest;
import com.example.BTL.model.response.room.RoomCreationResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoomMapper {
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "approvalStatus", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "images", ignore = true)
    Room toRoom(RoomCreationRequest request);

    @Mapping(source = "approvalStatus", target = "approvalStatus")
    @Mapping(source = "roomType", target = "roomType")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "user.tel", target = "landlordTel")
    @Mapping(source = "user.email", target = "landlordEmail")
    RoomCreationResponse toRoomResponse(Room room);
}
