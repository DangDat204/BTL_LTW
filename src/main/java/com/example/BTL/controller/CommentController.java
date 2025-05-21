package com.example.BTL.controller;

import com.example.BTL.model.ApiResponse;
import com.example.BTL.model.request.comment.CommentRequest;
import com.example.BTL.model.response.comment.CommentResponse;
import com.example.BTL.service.interfaces.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/createComment/{id}")
    @PreAuthorize("hasRole('TENANT')")
    @ResponseBody
    public ApiResponse<CommentResponse> createComment(@PathVariable Long id,
                                                      @Valid @RequestBody CommentRequest request){
        CommentResponse response = commentService.createComment(id, request);
        ApiResponse<CommentResponse> apiResponse = new ApiResponse<>(1000, "Comment created successfully", response);
        return apiResponse;
    }
//    All co the xem comment
    @GetMapping("/{id}")
    @ResponseBody
    public ApiResponse<List<CommentResponse>> getCommentsByRoomId(@PathVariable Long id) {
        List<CommentResponse> responses = commentService.getCommentsByRoomId(id);
        ApiResponse<List<CommentResponse>> apiResponse = new ApiResponse<>(1000, "Find all comment for room", responses);
        return apiResponse;
    }
}
