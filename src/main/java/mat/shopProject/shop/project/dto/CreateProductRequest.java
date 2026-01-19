package mat.shopProject.shop.project.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import mat.shopProject.shop.project.models.Category;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateProductRequest(

        @NotBlank(message = "Title is required")
        @Size(min = 1, max = 20, message = "Title must be between 1 and 20 characters")
        String title,

        @NotBlank(message = "Description is required")
        @Size(min = 1, max = 1000, message = "Description must be between 1 and 1000 characters")
        String description,

        @NotBlank(message = "Price is required")
        @Size(min = 1, max = 10, message = "Price must be between 1 and 10 characters")
        BigDecimal price,

        @NotBlank(message = "StockQuantity is required")
        @Size(min = 1, max = 10, message = "StockQuantity must be between 1 and 10 characters")
        int stockQuantity,

        @NotBlank(message = "Category is required")
        @Size(min = 1, max = 100, message = "Category must be between 1 and 100 characters")
        Category category
) {
}
