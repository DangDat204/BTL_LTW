package com.example.BTL.service.interfaces;

import com.example.BTL.model.request.user.RegisterRequest;
import com.example.BTL.model.request.user.UpdateUserRequest;
import com.example.BTL.model.response.user.RegisterResponse;
import org.springframework.data.domain.Page;

public interface UserService {
    RegisterResponse register(RegisterRequest request);
    RegisterResponse getCurrentUser();
    RegisterResponse updateUser(UpdateUserRequest request);
    RegisterResponse getInfoUser(Long id);
    Page<RegisterResponse> getAllUsers(int pageNumber, int pageSize);
}
