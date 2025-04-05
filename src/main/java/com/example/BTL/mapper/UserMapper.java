package com.example.BTL.mapper;
import com.example.BTL.entity.User;
import com.example.BTL.model.request.user.RegisterRequest;
import com.example.BTL.model.response.user.RegisterResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "role", ignore = true) // Bỏ qua role vì sẽ xử lý trong service
    @Mapping(target = "avatar", ignore = true) // Bỏ qua avatar vì sẽ xử lý trong service
    User toUser(RegisterRequest request);

    @Mapping(source = "role.roleName", target = "role")
//    ánh xạ role.roleName (lấy từ Role entity) sang role (String) trong RegisterResponse.
    RegisterResponse toRegisterResponse(User user);
}
