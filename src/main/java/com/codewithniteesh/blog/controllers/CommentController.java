package com.codewithniteesh.blog.controllers;

import com.codewithniteesh.blog.payloads.ApiResponse;
import com.codewithniteesh.blog.payloads.CommentDto;
import com.codewithniteesh.blog.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/post/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto,
                                                    @PathVariable int postId){
        return new ResponseEntity<>(commentService.createComment(commentDto, postId), HttpStatus.CREATED);
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable int commentId){
        commentService.deleteComment(commentId);
        return new ResponseEntity<>(new ApiResponse("Post with post id: "+commentId+" has been deleted successfully", true), HttpStatus.OK);
    }

    @GetMapping("/comments/{commentId}")
    public ResponseEntity<CommentDto> getComment(@PathVariable int commentId){
        return new ResponseEntity<>(commentService.getComment(commentId), HttpStatus.OK);
    }

}
