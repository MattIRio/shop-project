package mat.shopProject.shop.project.services;

import jakarta.transaction.Transactional;
import mat.shopProject.shop.project.dto.CartItemDto;
import mat.shopProject.shop.project.dto.CreateCategoryRequest;
import mat.shopProject.shop.project.models.AuthenticatedUser;
import mat.shopProject.shop.project.models.Category;
import mat.shopProject.shop.project.repositories.CategoriesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.text.Normalizer;
import java.util.List;
import java.util.UUID;

@Transactional
@Service
public class CategoriesService {

    @Autowired
    CategoriesRepo categoriesRepo;

    public List<Category> getCategories() {
        return categoriesRepo.findAll();
    }

    public Category createCategory(CreateCategoryRequest createCategoryRequest) {
        Category parentCategory = categoriesRepo.findById(createCategoryRequest.parentId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Parent category with id " + createCategoryRequest.parentId() + " not found"
                ));

        Category category = Category.builder()
                .parent(parentCategory)
                .name(createCategoryRequest.name())
                .slug(slugify(createCategoryRequest.name()))
                .build();

        return categoriesRepo.save(category);
    }

    public void deleteCategory(UUID categoryId) {
        Category category = categoriesRepo.findById(categoryId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Category with id " + categoryId + " not found"
                ));
        categoriesRepo.delete(category);
    }

    public Category changeCategory(UUID categoryId, CreateCategoryRequest createCategoryRequest) {
        Category parentCategory = categoriesRepo.findById(createCategoryRequest.parentId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Parent category with id " + createCategoryRequest.parentId() + " not found"
                ));
        Category currentCategory = categoriesRepo.findById(categoryId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Category with id " + createCategoryRequest.parentId() + " not found"
                ));

        currentCategory.setName(createCategoryRequest.name());
        currentCategory.setParent(parentCategory);
        currentCategory.setSlug(slugify(createCategoryRequest.name()));

        return categoriesRepo.save(currentCategory);
    }


    private String slugify(String input) {
        return Normalizer.normalize(input, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                .toLowerCase()
                .replaceAll("[^a-z0-9\\s-]", "")
                .trim()
                .replaceAll("\\s+", "-")
                .replaceAll("-+", "-");
    }
}

