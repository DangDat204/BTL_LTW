package com.example.BTL.model.request.Image;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageUploadRequest {
    @NotNull(message = "URL_REQUIRED")
    private String url; // URL của ảnh (hoặc có thể là dữ liệu base64 nếu bạn cho phép upload file)
    private Boolean isPrimary = false; // Ảnh chính hay không
}
