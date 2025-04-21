package com.example.BTL.service.Impl;

import com.example.BTL.entity.Image;
import com.example.BTL.entity.Room;
import com.example.BTL.exception.AppException;
import com.example.BTL.exception.ErrorCode;
import com.example.BTL.mapper.ImageMapper;
import com.example.BTL.model.request.Image.ImageUploadRequest;
import com.example.BTL.model.response.Image.ImageResponse;
import com.example.BTL.repository.ImageRepository;
import com.example.BTL.repository.RoomRepository;
import com.example.BTL.service.interfaces.ImageService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ImageMapper imageMapper;

    @Transactional
    @Override
    public List<ImageResponse> uploadImages(Long roomId, List<ImageUploadRequest> requests) {
        // Tìm phòng
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new AppException(ErrorCode.ROOM_NOT_FOUND));

        // Kiểm tra xem có ảnh nào được set là ảnh chính không
        boolean hasPrimaryImage = requests.stream().anyMatch(ImageUploadRequest::getIsPrimary);

        // Nếu có ảnh chính mới, cập nhật tất cả ảnh hiện tại của phòng thành ảnh phụ
        if (hasPrimaryImage) {
            List<Image> existingImages = imageRepository.findByRoomId(roomId);
            existingImages.forEach(image -> {
                image.setIsPrimary(false);
                imageRepository.save(image);
            });
        }

        // Tạo danh sách ảnh mới
        List<Image> newImages = requests.stream().map(request -> {
            Image image = imageMapper.toImage(request);
            image.setRoom(room);
            return image;
        }).collect(Collectors.toList());

        // Lưu ảnh mới
        List<Image> savedImages = imageRepository.saveAll(newImages);

        // Trả về response
        return savedImages.stream()
                .map(imageMapper::toImageResponse)
                .collect(Collectors.toList());
    }
}

