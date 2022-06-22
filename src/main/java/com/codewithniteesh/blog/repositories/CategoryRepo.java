package com.codewithniteesh.blog.repositories;

import com.codewithniteesh.blog.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category, Integer> {
}
