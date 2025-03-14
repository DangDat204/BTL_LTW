package com.example.BTL.model.request.user;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class RegisterRequest {

    @NonNull
    @Size(min = 3,message = "USERNAME_INVALID")
//    day la USERNAME_INVALID trong ErrorCode chu khong phai la 1 String
    private String username;

    @NonNull
    @Size(min = 5, message = "INVALID_PASSWORD")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d).+$", message = "Password must contain both letters and numbers")
    private String password;

    @Pattern(regexp = "^.+@gmail.com$", message = "INVALID_EMAIL")
    private String email;

    @NonNull
    private String fullname;

    @Pattern(regexp = "^\\d{10}$", message = "INVALID_TEL")
    private String tel;

    private String role_id;

}
