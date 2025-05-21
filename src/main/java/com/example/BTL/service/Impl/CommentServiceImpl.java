package com.example.BTL.service.Impl;

import com.example.BTL.entity.Comment;
import com.example.BTL.entity.Room;
import com.example.BTL.entity.User;
import com.example.BTL.exception.AppException;
import com.example.BTL.exception.ErrorCode;
import com.example.BTL.mapper.CommentMapper;
import com.example.BTL.model.request.comment.CommentRequest;
import com.example.BTL.model.response.comment.CommentResponse;
import com.example.BTL.repository.CommentRepository;
import com.example.BTL.repository.RoomRepository;
import com.example.BTL.repository.UserRepository;
import com.example.BTL.service.interfaces.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private CommentRepository commentRepository;

    @PreAuthorize("hasRole('TENANT')")
    @Override
    public CommentResponse createComment(Long roomId, CommentRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User tenant = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        Room room = roomRepository.findById(roomId)
                .orElseThrow(()->new AppException(ErrorCode.ROOM_NOT_FOUND));
        Comment comment = commentMapper.toComment(request);
        comment.setRoom(room);
        comment.setTenant(tenant);
        commentRepository.save(comment);
        return commentMapper.toCommentResponse(comment);
    }

    @Override
    public List<CommentResponse> getCommentsByRoomId(Long roomId) {
        List<Comment> comments = commentRepository.findByRoomIdOrderByCreatedAtDesc(roomId);
        return comments.stream()
                .map(commentMapper::toCommentResponse)
                .collect(Collectors.toList());
    }
}
