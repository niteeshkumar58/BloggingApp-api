package com.codewithniteesh.blog.controllers;

import com.codewithniteesh.blog.payloads.ApiResponse;
import com.codewithniteesh.blog.payloads.PostDto;
import com.codewithniteesh.blog.payloads.PostResponse;
import com.codewithniteesh.blog.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(
            @Valid
            @RequestBody PostDto postDto,
            @PathVariable Integer userId,
            @PathVariable Integer categoryId){

        PostDto createdPostDto = postService.createPost(postDto, userId, categoryId);

        return new ResponseEntity<>(createdPostDto, HttpStatus.CREATED);
    }

    //update
    @PutMapping("/user/{userId}/category/{categoryId}/post/{postId}")
    public ResponseEntity<PostDto> updatePost(
            @Valid
            @RequestBody PostDto postDto,
            @PathVariable Integer userId,
            @PathVariable Integer categoryId,
            @PathVariable Integer postId){

        PostDto updatedPostDto = postService.updatePost(postDto, postId, userId, categoryId);

        return new ResponseEntity<>(updatedPostDto, HttpStatus.CREATED);
    }

    //get
    @GetMapping("/post/{postId}")
    public ResponseEntity<PostDto> getPost(
            @PathVariable Integer postId){

        PostDto postDto = postService.getPost(postId);

        return new ResponseEntity<>(postDto, HttpStatus.OK);
    }


    //get all
    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPost(
            @RequestParam(value = "pageNumber", defaultValue = "1", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = "postId", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
        ){

        PostResponse postDtoList = postService.getAllPosts(pageNumber-1, pageSize, sortBy, sortDir); //pageNumber is starts from zero

        return new ResponseEntity<>(postDtoList, HttpStatus.OK);
    }

    //delete
    @DeleteMapping("/post/{postId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId){

        postService.deletePost(postId);

        return new ResponseEntity<>(new ApiResponse("Post with postId "+postId+" has been successfully deleted !!!", true), HttpStatus.CREATED);
    }
    //get by user
    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable Integer userId) {
        List<PostDto> postDtoList = postService.getPostsByUser(userId);

        return new ResponseEntity<>(postDtoList, HttpStatus.OK);
    }

        //get by category
    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Integer categoryId) {
        List<PostDto> postDtoList = postService.getPostsByCategory(categoryId);

        return new ResponseEntity<>(postDtoList, HttpStatus.OK);
    }

    //search
    @GetMapping("/posts/search")
    public ResponseEntity<List<PostDto>> searchPosts(@RequestParam(value = "keyword") String keyword){
        return new ResponseEntity<>(postService.searchPosts(keyword), HttpStatus.OK);
    }

}
