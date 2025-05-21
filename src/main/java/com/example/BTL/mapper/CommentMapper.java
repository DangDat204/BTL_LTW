package com.example.BTL.mapper;

import com.example.BTL.entity.Comment;
import com.example.BTL.model.request.comment.CommentRequest;
import com.example.BTL.model.response.comment.CommentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mapping(target = "room", ignore = true)
    @Mapping(target = "tenant", ignore = true)
    Comment toComment(CommentRequest request);

    @Mapping(source = "room.id", target = "roomId")
    @Mapping(source = "tenant.id", target = "tenantId")
    @Mapping(source = "tenant.username", target = "tenantUsername")
    @Mapping(source = "createdAt", target = "createdAt")
    CommentResponse toCommentResponse(Comment comment);
}
