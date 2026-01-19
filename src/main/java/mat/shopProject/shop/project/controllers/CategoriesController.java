package mat.shopProject.shop.project.controllers;


import mat.shopProject.shop.project.dto.CartItemDto;
import mat.shopProject.shop.project.dto.CreateCategoryRequest;
import mat.shopProject.shop.project.models.Category;
import mat.shopProject.shop.project.services.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class CategoriesController {

    @Autowired
    CategoriesService categoriesService;

    @GetMapping("/api/v1/categories")
    public ResponseEntity<List<Category>> getAllCategories(){
        return ResponseEntity.ok(categoriesService.getCategories());
    }

    @PostMapping("/api/v1/categories")
    public ResponseEntity<Category> createCategory(@RequestBody CreateCategoryRequest createCategoryRequest){
        return ResponseEntity.ok(categoriesService.createCategory(createCategoryRequest));
    }

    @PutMapping("/api/v1/categories/{categoryId}")
    public ResponseEntity<Category> changeCategory(@PathVariable UUID categoryId, @RequestBody CreateCategoryRequest createCategoryRequest){
        return ResponseEntity.ok(categoriesService.changeCategory(categoryId, createCategoryRequest));
    }

    @DeleteMapping("/api/v1/categories/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable UUID categoryId){
        categoriesService.deleteCategory(categoryId);
        return ResponseEntity.noContent().build();
    }
}
