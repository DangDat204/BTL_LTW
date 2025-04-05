package com.example.BTL.model.response.user;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class RegisterResponse {
    private Long id;
    private String username;
    private String email;
    private String fullname;
    private String tel;
    private String role;

}
