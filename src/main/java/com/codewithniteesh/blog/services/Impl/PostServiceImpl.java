package com.codewithniteesh.blog.services.Impl;

import com.codewithniteesh.blog.entities.Category;
import com.codewithniteesh.blog.entities.Post;
import com.codewithniteesh.blog.entities.User;
import com.codewithniteesh.blog.exceptions.ResourceNotFoundException;
import com.codewithniteesh.blog.payloads.PostDto;
import com.codewithniteesh.blog.payloads.PostResponse;
import com.codewithniteesh.blog.repositories.CategoryRepo;
import com.codewithniteesh.blog.repositories.PostRepo;
import com.codewithniteesh.blog.repositories.UserRepo;
import com.codewithniteesh.blog.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PostRepo postRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CategoryRepo categoryRepo;


    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
        Post newPost = modelMapper.map(postDto, Post.class);
        newPost.setImageName("default.png");
        newPost.setAddedDate(new Date());

        User user = userRepo.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User", "UserId", userId));

        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException("Category", "categoryId", categoryId));

        newPost.setUser(user);
        newPost.setCategory(category);

        return modelMapper.map(postRepo.save(newPost), PostDto.class );
    }

    @Override
    public PostDto updatePost(PostDto postDto, int postId, Integer userId, Integer categoryId) {
        Post oldPost = postRepo.findById(postId)
                .orElseThrow(()->new ResourceNotFoundException("Post", "Id", postId));
        oldPost.setTitle(postDto.getTitle());
        oldPost.setContent(postDto.getContent());
        if(postDto.getImageName()!=null)
            oldPost.setImageName(postDto.getImageName());

        return modelMapper.map((postRepo.save(oldPost)), PostDto.class);
    }

    @Override
    public PostDto getPost(int postId) {
        Post post = postRepo.findById(postId)
                .orElseThrow(()->new ResourceNotFoundException("Post", "Id", postId));
        return modelMapper.map(post, PostDto.class);
    }

    @Override
    public PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Sort sort = null;
        if(sortDir.equalsIgnoreCase("desc"))
            sort = Sort.by(sortBy).descending();
        else
            sort = Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Post> postPage = postRepo.findAll(pageable);

        List<Post> postList = postPage.getContent();

        List<PostDto> postDtoList = postList.stream()
                .map(post -> modelMapper.map(post, PostDto.class)).toList();

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDtoList);
        postResponse.setTotalPages(postPage.getTotalPages());
        postResponse.setLastPage(postPage.isLast());
        postResponse.setPageSize(postPage.getSize());
        postResponse.setTotalElements(postPage.getTotalElements());
        postResponse.setPageNumber(postPage.getNumber());

        return postResponse;
    }

    @Override
    public void deletePost(int postId) {
        Post post = postRepo.findById(postId)
                .orElseThrow(()->new ResourceNotFoundException("Post", "Id", postId));
        postRepo.delete(post);
    }

    @Override
    public List<PostDto> getPostsByCategory(int categoryId) {
        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException("Category", "Id", categoryId));
        List<Post> postList = postRepo.findByCategory(category);

        List<PostDto> postDtoList = postList.stream()
                .map(post -> modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());

        return postDtoList;
    }

    @Override
    public List<PostDto> getPostsByUser(int userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User", "Id", userId));
        List<Post> postList = postRepo.findByUser(user);

        return postList.stream()
                .map(post -> modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<PostDto> searchPosts(String keyword) {

        return postRepo.findByTitleContaining(keyword).stream()
                .map(post -> modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());
    }
}
