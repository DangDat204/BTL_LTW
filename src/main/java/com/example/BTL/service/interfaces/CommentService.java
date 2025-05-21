package com.example.BTL.service.interfaces;

import com.example.BTL.model.request.comment.CommentRequest;
import com.example.BTL.model.response.comment.CommentResponse;

import java.util.List;

public interface CommentService {
    CommentResponse createComment(Long roomId, CommentRequest request);
    List<CommentResponse> getCommentsByRoomId(Long roomId);
}
