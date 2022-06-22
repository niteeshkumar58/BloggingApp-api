package com.codewithniteesh.blog.services;

import com.codewithniteesh.blog.entities.Post;
import com.codewithniteesh.blog.payloads.PostDto;
import com.codewithniteesh.blog.payloads.PostResponse;

import java.util.List;

public interface PostService {
    //create post
    PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);

    //update post
    PostDto updatePost(PostDto postDto, int postId, Integer userId, Integer categoryId);

    //get post
    PostDto getPost(int postId);

    //get all post
    PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    //delete post
    void deletePost(int postId);

    List<PostDto> getPostsByCategory(int categoryId);

    List<PostDto> getPostsByUser(int userId);

    List<PostDto> searchPosts(String keyword);
}
