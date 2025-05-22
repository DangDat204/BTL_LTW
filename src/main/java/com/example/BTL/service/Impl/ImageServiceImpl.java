package com.example.BTL.service.Impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.BTL.entity.Image;
import com.example.BTL.entity.Room;
import com.example.BTL.entity.User;
import com.example.BTL.exception.AppException;
import com.example.BTL.exception.ErrorCode;
import com.example.BTL.model.response.Image.ImageResponse;
import com.example.BTL.repository.ImageRepository;
import com.example.BTL.repository.RoomRepository;
import com.example.BTL.repository.UserRepository;
import com.example.BTL.service.interfaces.ImageService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private Cloudinary cloudinary;
    @Autowired
    private UserRepository userRepository;

    @Transactional
    @Override
    public List<ImageResponse> uploadRoomImages(Long roomId, MultipartFile[] files) {
        
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User landlord = userRepository.findUserByUsername(username)
                .orElseThrow(()->new AppException(ErrorCode.USER_NOT_EXISTED));
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new AppException(ErrorCode.ROOM_NOT_FOUND));
        // Kiểm tra quyền: Chỉ landlord sở hữu phòng được sửa
        if (!room.getUser().getId().equals(landlord.getId())) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
        List<ImageResponse> result = new ArrayList<>();
        for (MultipartFile file : files) {
            try {
                Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
                String imageUrl = (String) uploadResult.get("secure_url");

                Image image = new Image();
                image.setUrl(imageUrl);
                image.setRoom(room);
                image = imageRepository.save(image);

                result.add(new ImageResponse(image.getId(), image.getUrl()));

            } catch (IOException e) {
                throw new RuntimeException("Upload failed", e);
            }
        }
        return result;
    }



}

