package com.example.BTL.service;

import com.example.BTL.model.request.user.RegisterRequest;
import com.example.BTL.model.response.user.RegisterResponse;

public interface UserService {
    RegisterResponse register(RegisterRequest request);
}
