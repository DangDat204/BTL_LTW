package com.example.BTL.model.request.Image;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageCreationRequest {
    private String url; // URL của ảnh (hoặc có thể là dữ liệu base64 nếu bạn cho phép upload file)
    private Boolean isPrimary; // Ảnh chính hay không
}
