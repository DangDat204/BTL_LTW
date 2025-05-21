package com.example.BTL.repository;

import com.example.BTL.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByRoomIdOrderByCreatedAtDesc(Long roomId);
}
