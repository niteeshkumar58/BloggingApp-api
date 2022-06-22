package com.codewithniteesh.blog.controllers;

import com.codewithniteesh.blog.payloads.CategoryDto;
import com.codewithniteesh.blog.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto category){
        CategoryDto createdCategory = categoryService.createCategory(category);

        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable("categoryId") int categoryId){
        CategoryDto fetchedCategory = categoryService.getCategoryById(categoryId);

        return ResponseEntity.ok(fetchedCategory);
//        return new ResponseEntity<>(fetchedCategory, HttpStatus.OK);
    }

    //update
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto category, @PathVariable("categoryId") int categoryId){
        CategoryDto updatedCategory = categoryService.updateCategory(category, categoryId);
        return ResponseEntity.ok(updatedCategory);
    }

    //getAll
    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getAllCategory(){
        List<CategoryDto> categoryDtoList = categoryService.getAllCategory();
        return ResponseEntity.ok(categoryDtoList);
    }

    //delete
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Map<String, String>> deleteCategory(@PathVariable int categoryId){
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok(Map.of("message", "Category with id "+categoryId+" deleted successfully !!!"));
    }

}
