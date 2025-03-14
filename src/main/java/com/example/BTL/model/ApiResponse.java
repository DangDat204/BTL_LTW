package com.example.BTL.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

@JsonInclude(JsonInclude.Include.NON_NULL)
//bỏ qua các thuộc tính có giá trị null khi serialize (chuyển đổi object thành JSON)

// de tao doi tuong tra ve theo cach ta quy dinh, co the la loi, co the la object
public class ApiResponse <T> {
    @Builder.Default
//  gia tri int khi build ma k set lai se mac dinh la 0 ne can dung @Builder.Default
    int code = 1000;
    //    code 1000 success
    String message;
    T result;

}