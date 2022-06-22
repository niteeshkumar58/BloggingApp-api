package com.codewithniteesh.blog.services.Impl;

import com.codewithniteesh.blog.entities.Category;
import com.codewithniteesh.blog.exceptions.ResourceNotFoundException;
import com.codewithniteesh.blog.payloads.CategoryDto;
import com.codewithniteesh.blog.repositories.CategoryRepo;
import com.codewithniteesh.blog.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category createdCategory = categoryRepo.save(categoryDtoToCategory(categoryDto));
        return categoryToCategoryDto(createdCategory);
    }

    @Override
    public CategoryDto getCategoryById(int categoryId) {
        Category fetchedCategory = categoryRepo.findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException("Category", "Id", categoryId));
        return categoryToCategoryDto(fetchedCategory);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, int categoryId) {
        Category fetchedCategory = categoryRepo.findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException("Category", "Id", categoryId));

        fetchedCategory.setCategoryType(categoryDto.getCategoryType());
        fetchedCategory.setCategoryType(categoryDto.getCategoryType());

        return categoryToCategoryDto(categoryRepo.save(fetchedCategory));
    }



    @Override
    public List<CategoryDto> getAllCategory() {
        List<Category> categoryList = categoryRepo.findAll();

        List<CategoryDto> categoryDtoList = categoryList
                .stream()
                .map(category -> categoryToCategoryDto(category))
                .collect(Collectors.toList());

        return categoryDtoList;
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category", "Id", categoryId));
        categoryRepo.delete(category);
    }

    private CategoryDto categoryToCategoryDto(Category category){
        CategoryDto categoryDto = modelMapper.map(category, CategoryDto.class);
        return categoryDto;
    }

    private Category categoryDtoToCategory(CategoryDto categoryDto){
        Category category = modelMapper.map(categoryDto, Category.class);
        return category;
    }
}
