package com.example.BTL.service.Impl;

import com.example.BTL.entity.Room;
import com.example.BTL.entity.User;
import com.example.BTL.enums.RoomApprovalStatus;
import com.example.BTL.enums.RoomStatus;
import com.example.BTL.enums.RoomType;
import com.example.BTL.exception.AppException;
import com.example.BTL.exception.ErrorCode;
import com.example.BTL.mapper.RoomMapper;
import com.example.BTL.model.request.room.RoomCreationRequest;
import com.example.BTL.model.response.room.RoomCreationResponse;
import com.example.BTL.repository.RoomRepository;
import com.example.BTL.repository.UserRepository;
import com.example.BTL.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoomMapper roomMapper;
    @Autowired
    private RoomRepository roomRepository;

    @Override
    public RoomCreationResponse createRoom(RoomCreationRequest request) {
        // Lấy user hiện tại (landlord) từ token
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User landlord = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        // Ánh xạ từ request sang Room entity
        Room room = roomMapper.toRoom(request);

        // Set các giá trị mặc định
        room.setUser(landlord);
        room.setApprovalStatus(RoomApprovalStatus.PENDING); // Chờ duyệt
        room.setStatus(RoomStatus.AVAILABLE); // Mặc định là available

        // Xử lý roomType
        if (request.getRoomType() != null) {
            room.setRoomType(RoomType.fromValue(request.getRoomType()));
        }

        // Lưu phòng
        Room savedRoom = roomRepository.save(room);

        // Trả về response
        return roomMapper.toRoomResponse(savedRoom);
    }
}
