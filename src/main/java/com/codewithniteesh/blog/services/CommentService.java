package com.codewithniteesh.blog.services;


import com.codewithniteesh.blog.entities.Comment;
import com.codewithniteesh.blog.payloads.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto getComment(int commentId);
    CommentDto createComment(CommentDto comment, int postId);
    void deleteComment(int commentId);
}
