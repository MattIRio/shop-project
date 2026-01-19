package mat.shopProject.shop.project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import mat.shopProject.shop.project.models.Category;

import java.util.UUID;

public record CreateCategoryRequest(

        @NotBlank(message = "Name is required")
        @Size(min = 1, max = 50, message = "Title must be between 1 and 50 characters")
        String name,

        UUID parentId
) {
}
