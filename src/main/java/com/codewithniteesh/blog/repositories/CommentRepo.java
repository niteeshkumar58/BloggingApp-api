package com.codewithniteesh.blog.repositories;

import com.codewithniteesh.blog.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comment, Integer> {

}
