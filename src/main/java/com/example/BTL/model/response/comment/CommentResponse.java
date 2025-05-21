package com.example.BTL.model.response.comment;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class CommentResponse {
    private Long id;
    private Long roomId;
    private Long tenantId;
    private String tenantUsername;
    private String content;
    private Integer rating;
    private LocalDateTime createdAt;
}
