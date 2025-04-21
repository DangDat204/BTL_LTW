package com.example.BTL.service.interfaces;

import com.example.BTL.model.request.Image.ImageUploadRequest;
import com.example.BTL.model.response.Image.ImageResponse;

import java.util.List;

public interface ImageService {
    List<ImageResponse> uploadImages(Long roomId, List<ImageUploadRequest> requests);
}
