package com.example.BTL.model.request.Authentication;

import lombok.*;
import lombok.experimental.FieldDefaults;

// class nay nguoi dung cung cap username va password de xac nhan dung tai khoan da tao
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationRequest {
    String username;
    String password;
}
