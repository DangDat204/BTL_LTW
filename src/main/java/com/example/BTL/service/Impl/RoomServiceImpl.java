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
import com.example.BTL.model.request.room.RoomApprovalRequest;
import com.example.BTL.model.response.room.RoomCreationResponse;
import com.example.BTL.repository.RoomRepository;
import com.example.BTL.repository.UserRepository;
import com.example.BTL.service.interfaces.RoomService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoomMapper roomMapper;
    @Autowired
    private RoomRepository roomRepository;


    @Transactional
    @PreAuthorize("hasRole('LANDLORD')")
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

    @PreAuthorize("hasRole('LANDLORD')")
    @Override
    public RoomCreationResponse updateRoom(Long roomId, RoomCreationRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User landlord = userRepository.findUserByUsername(username)
                .orElseThrow(()->new AppException(ErrorCode.USER_NOT_EXISTED));
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new AppException(ErrorCode.ROOM_NOT_FOUND));
        // Kiểm tra quyền: Chỉ landlord sở hữu phòng được sửa
        if (!room.getUser().getId().equals(landlord.getId())) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
        // Cập nhật thông tin phòng
        room.setAddress(request.getAddress());
        room.setArea(request.getArea());
        room.setCapacity(request.getCapacity());
        room.setDescription(request.getDescription());
        room.setPrice(request.getPrice());
        room.setAmenities(request.getAmenities());
        if (request.getRoomType() != null) {
            room.setRoomType(RoomType.fromValue(request.getRoomType()));
        }
        Room updatedRoom = roomRepository.save(room);

        // Trả về response
        return roomMapper.toRoomResponse(updatedRoom);
    }
// xoa phong
    @PreAuthorize("hasRole('LANDLORD')")
    @Override
    public void deleteRoom(Long roomId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User landlord = userRepository.findUserByUsername(username)
                .orElseThrow(()->new AppException(ErrorCode.USER_NOT_EXISTED));
        Room room = roomRepository.findByIdAndDeletedFalse(roomId)
                .orElseThrow(()->new AppException(ErrorCode.ROOM_NOT_FOUND));
        // Kiểm tra quyền: Chỉ landlord sở hữu phòng được xoa
        if (!room.getUser().getId().equals(landlord.getId())) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
        room.setDeleted(true);
        roomRepository.save(room);
    }
// khoi phuc phong
    @PreAuthorize("hasRole('LANDLORD')")
    @Override
    public void restoreRoom(Long roomId){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User landlord = userRepository.findUserByUsername(username)
                .orElseThrow(()->new AppException(ErrorCode.USER_NOT_EXISTED));
        Room room = roomRepository.findByIdAndDeletedTrue(roomId)
                .orElseThrow(()->new AppException(ErrorCode.ROOM_NOT_FOUND));
        // Kiểm tra quyền: Chỉ landlord sở hữu phòng được xoa
        if (!room.getUser().getId().equals(landlord.getId())) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
        room.setDeleted(false);
        roomRepository.save(room);
    }
    // Lấy danh sách phòng chua duyet của landlord hiện tại
    @PreAuthorize("hasRole('LANDLORD')")
    @Override
    public Page<RoomCreationResponse> getMyRoomsPending(int pageNumber, int pageSize) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User landlord = userRepository.findUserByUsername(username)
                .orElseThrow(()->new AppException(ErrorCode.USER_NOT_EXISTED));
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);

        Page<Room> roomPage = roomRepository.findByUserIdAndApprovalStatusAndDeletedFalse(landlord.getId(), RoomApprovalStatus.PENDING, pageable);
        List<RoomCreationResponse> roomResponses = roomPage.getContent().stream()
                .map(roomMapper::toRoomResponse)
                .collect(Collectors.toList());

        return new PageImpl<>(roomResponses, pageable, roomPage.getTotalElements());
    }
    @PreAuthorize("hasRole('LANDLORD')")
    @Override
    public Page<RoomCreationResponse> getMyRoomsApproved(int pageNumber, int pageSize) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User landlord = userRepository.findUserByUsername(username)
                .orElseThrow(()->new AppException(ErrorCode.USER_NOT_EXISTED));
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        Page<Room> roomPage = roomRepository.findByUserIdAndApprovalStatusAndDeletedFalse(landlord.getId(), RoomApprovalStatus.APPROVED, pageable);
        List<RoomCreationResponse> roomResponses = roomPage.getContent().stream()
                .map(roomMapper::toRoomResponse)
                .collect(Collectors.toList());

        return new PageImpl<>(roomResponses, pageable, roomPage.getTotalElements());
    }

    @Override
    public Page<RoomCreationResponse> getAllRooms(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.by("price").ascending());
        Page<Room> roomPage = roomRepository.findByApprovalStatusAndDeletedFalse(RoomApprovalStatus.APPROVED, pageable);

        List<RoomCreationResponse> roomResponses = roomPage.getContent().stream()
                .map(roomMapper::toRoomResponse)
                .peek(response -> {
                    response.setLandlordTel(null);
                    response.setLandlordEmail(null);
                })
                .collect(Collectors.toList());

        return new PageImpl<>(roomResponses, pageable, roomPage.getTotalElements());
    }

    // Lấy chi tiết phòng
    @Override
    public RoomCreationResponse getRoomById(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ROOM_NOT_FOUND));

        // Trả về đầy đủ thông tin, bao gồm thông tin liên hệ
        return roomMapper.toRoomResponse(room);
    }
// filterRoom
    @Override
    public Page<RoomCreationResponse> filterRooms(Double minPrice, Double maxPrice,
                                  Double minArea, Double maxArea,
                                  String address, RoomType roomType, int pageNumber, int pageSize){
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.by("price").ascending());
        Page<Room> roomPage = roomRepository.filterRooms(minPrice, maxPrice, minArea, maxArea, address, roomType, pageable);
        List<RoomCreationResponse> roomResponses = roomPage.getContent().stream()
                .map(roomMapper::toRoomResponse)
                .peek(response -> {
                    response.setLandlordTel(null);
                    response.setLandlordEmail(null);
                })
                .collect(Collectors.toList());

        return new PageImpl<>(roomResponses, pageable, roomPage.getTotalElements());
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public RoomCreationResponse approveRoom(Long roomId, RoomApprovalRequest request) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new AppException(ErrorCode.ROOM_NOT_FOUND));
// nhan vao string chuyen qua enum
        RoomApprovalStatus approvalStatus = RoomApprovalStatus.fromValue(request.getApprovalStatus());
        room.setApprovalStatus(approvalStatus);
        Room updateRoom = roomRepository.save(room);
        return roomMapper.toRoomResponse(updateRoom);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public Page<RoomCreationResponse> getPendingRooms(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize); // Lưu ý pageNumber - 1
        Page<Room> roomPage = roomRepository.findByApprovalStatusAndDeletedFalse(RoomApprovalStatus.PENDING, pageable);

        List<RoomCreationResponse> responses = roomPage.getContent().stream()
                .map(roomMapper::toRoomResponse)
                .collect(Collectors.toList());

        return new PageImpl<>(responses, pageable, roomPage.getTotalElements());
    }

}
