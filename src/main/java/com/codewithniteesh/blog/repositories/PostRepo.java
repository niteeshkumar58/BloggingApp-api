package com.codewithniteesh.blog.repositories;

import com.codewithniteesh.blog.entities.Category;
import com.codewithniteesh.blog.entities.Post;
import com.codewithniteesh.blog.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<Post, Integer> {

    //to find all post of an user
    List<Post> findByUser(User user);

    // to find all post by category
    List<Post> findByCategory(Category category);

    List<Post> findByTitleContaining(String keyword);
}
