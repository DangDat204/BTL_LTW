package com.example.BTL.mapper;

import com.example.BTL.entity.Image;
import com.example.BTL.model.request.Image.ImageUploadRequest;
import com.example.BTL.model.response.Image.ImageResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ImageMapper {
    @Mapping(target = "room", ignore = true)
    Image toImage(ImageUploadRequest request);

    ImageResponse toImageResponse(Image image);
}
