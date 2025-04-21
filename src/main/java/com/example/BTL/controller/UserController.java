package com.example.BTL.controller;

import com.example.BTL.model.ApiResponse;
import com.example.BTL.model.request.user.RegisterRequest;
import com.example.BTL.model.response.user.RegisterResponse;
import com.example.BTL.repository.UserRepository;
import com.example.BTL.service.interfaces.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login(){
        return "user/login.html";
    }

    @GetMapping("/register")
    public String register(){
        return "user/register";
    }

    @PostMapping("/register")
    @ResponseBody
    public ApiResponse<RegisterResponse> registerUser(@RequestBody @Valid RegisterRequest request){
        ApiResponse<RegisterResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.register(request));
        return apiResponse;
    }

    @GetMapping("/getMyInfo")
    @ResponseBody
    public ApiResponse<RegisterResponse> getCurrentUser(){
        RegisterResponse response = userService.getCurrentUser();
        ApiResponse<RegisterResponse> apiResponse = new ApiResponse<>(1000, "Get info user", response);
        return  apiResponse;
    }
}
