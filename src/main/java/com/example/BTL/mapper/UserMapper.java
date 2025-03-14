package com.example.BTL.mapper;
import com.example.BTL.entity.User;
import com.example.BTL.model.request.user.RegisterRequest;
import com.example.BTL.model.response.user.RegisterResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(RegisterRequest request);
    RegisterResponse toRegisterResponse(User user);
}
