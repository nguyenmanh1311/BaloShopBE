package com.hcmute.baloshop.repositories;

import com.hcmute.baloshop.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {
}
