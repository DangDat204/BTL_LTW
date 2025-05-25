package com.example.BTL.controller;

import com.example.BTL.entity.User;
import com.example.BTL.model.ApiResponse;
import com.example.BTL.model.request.user.RegisterRequest;
import com.example.BTL.model.request.user.UpdateUserRequest;
import com.example.BTL.model.response.user.RegisterResponse;
import com.example.BTL.service.interfaces.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

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
        ApiResponse<RegisterResponse> apiResponse = new ApiResponse<>(1000, "Get info current user", response);
        return  apiResponse;
    }
    @PutMapping("/updateUser")
    @ResponseBody
    public ApiResponse<RegisterResponse> updateUser(@RequestBody @Valid UpdateUserRequest request){
        ApiResponse<RegisterResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.updateUser(request));
        apiResponse.setMessage("update user");
        return apiResponse;
    }

    @GetMapping("/getInfoUser/{id}")
    @ResponseBody
    public ApiResponse<RegisterResponse> getInfoUser(@PathVariable Long id){
        RegisterResponse response = userService.getInfoUser(id);
        ApiResponse<RegisterResponse> apiResponse = new ApiResponse<>(1000, "Get info user", response);
        return  apiResponse;
    }

}
