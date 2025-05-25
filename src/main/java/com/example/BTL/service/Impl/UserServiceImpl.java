package com.example.BTL.service.Impl;


import com.example.BTL.entity.Avatar;
import com.example.BTL.entity.Role;
import com.example.BTL.entity.User;
import com.example.BTL.enums.RoleEnum;
import com.example.BTL.exception.AppException;
import com.example.BTL.exception.ErrorCode;
import com.example.BTL.mapper.UserMapper;
import com.example.BTL.model.request.user.RegisterRequest;
import com.example.BTL.model.request.user.UpdateUserRequest;
import com.example.BTL.model.response.user.RegisterResponse;
import com.example.BTL.repository.RoleRepository;
import com.example.BTL.repository.UserRepository;
import com.example.BTL.service.interfaces.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    @Transactional
    public RegisterResponse register(RegisterRequest registerRequest) {
        if (userRepository.existsByUsername(registerRequest.getUsername()))
            throw new AppException(ErrorCode.USER_EXISTED);
        if (userRepository.existsByEmail(registerRequest.getEmail()))
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        var user = userMapper.toUser(registerRequest);
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        // Tìm Role dựa trên role name
        RoleEnum roleEnum = RoleEnum.fromValue(registerRequest.getRole());
        Role role = roleRepository.findByRoleName(roleEnum);
        if (role == null) {
            throw new AppException(ErrorCode.INVALID_ROLE);
        }
        user.setRole(role);

        // Tạo Avatar cho user
        Avatar avatar = new Avatar();
        avatar.setUser(user);
        avatar.setUrl("/images/Logo-ptit.png");
        user.setAvatar(avatar);

        return userMapper.toRegisterResponse(userRepository.save(user));
    }

    @Override
    public RegisterResponse getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return userMapper.toRegisterResponse(user);
    }

    @Override
    public RegisterResponse updateUser(UpdateUserRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        if (!user.getEmail().equals(request.getEmail())) {
            if (userRepository.existsByEmail(request.getEmail()))
                throw new AppException(ErrorCode.EMAIL_EXISTED);
        }
        user.setFullname(request.getFullname());
        user.setTel(request.getTel());
        user.setEmail(request.getEmail());
        // Chỉ cập nhật mật khẩu nếu được cung cấp
        if (request.getPassword() != null && !request.getPassword().trim().isEmpty()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        return userMapper.toRegisterResponse(userRepository.save(user));
    }

    @Override
    public RegisterResponse getInfoUser(Long id){
        var user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        RegisterResponse response = userMapper.toRegisterResponse(user);
        return response;
    }
//    admin
    @Override
    public Page<RegisterResponse> getAllUsers(int pageNumber, int pageSize){
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);

        Page<User> userPage = userRepository.findAll(pageable);
        List<RegisterResponse> responses = userPage.getContent().stream()
                .map(userMapper::toRegisterResponse)
                .collect(Collectors.toList());

        return new PageImpl<>(responses, pageable, userPage.getTotalElements());
    }
}
