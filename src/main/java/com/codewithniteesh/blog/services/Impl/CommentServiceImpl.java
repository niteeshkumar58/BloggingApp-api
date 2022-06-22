package com.codewithniteesh.blog.services.Impl;

import com.codewithniteesh.blog.entities.Comment;
import com.codewithniteesh.blog.entities.Post;
import com.codewithniteesh.blog.exceptions.ResourceNotFoundException;
import com.codewithniteesh.blog.payloads.CommentDto;
import com.codewithniteesh.blog.repositories.CommentRepo;
import com.codewithniteesh.blog.repositories.PostRepo;
import com.codewithniteesh.blog.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepo commentRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PostRepo postRepo;

    @Override
    public CommentDto getComment(int commentId) {
        return modelMapper.map(commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment", "Id", commentId)),CommentDto.class);
    }

    @Override
    public CommentDto createComment(CommentDto commentDto, int postId) {
        Comment comment = modelMapper.map(commentDto, Comment.class);
        Post post = postRepo.findById(postId)
                .orElseThrow(()->new ResourceNotFoundException("Post", "Id", postId));
        comment.setPost(post);
        return modelMapper.map(commentRepo.save(comment), CommentDto.class);
    }

    @Override
    public void deleteComment(int commentId) {
        Comment comment = commentRepo
                .findById(commentId)
                .orElseThrow(()->new ResourceNotFoundException("Comment", "Id", commentId));
        commentRepo.delete(comment);
    }
}
