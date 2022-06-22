package com.codewithniteesh.blog.services;

import com.codewithniteesh.blog.entities.Category;
import com.codewithniteesh.blog.payloads.CategoryDto;

import java.util.List;

public interface CategoryService {
    public CategoryDto createCategory(CategoryDto categoryDto);
    public CategoryDto getCategoryById(int categoryId);
    public CategoryDto updateCategory(CategoryDto categoryDto, int categoryId);
    public List<CategoryDto> getAllCategory();
    public void deleteCategory(Integer categoryId);
}
