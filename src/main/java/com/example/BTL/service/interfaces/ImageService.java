package com.example.BTL.service.interfaces;

import com.example.BTL.model.request.Image.ImageUploadRequest;
import com.example.BTL.model.response.Image.ImageResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
    List<ImageResponse> uploadRoomImages(Long roomId, MultipartFile[] files);
}
