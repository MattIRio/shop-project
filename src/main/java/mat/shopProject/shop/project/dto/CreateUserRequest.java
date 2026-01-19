package mat.shopProject.shop.project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserRequest(

    @NotBlank(message = "Username is required")
    @Size(min = 1, max = 20, message = "Username must be between 1 and 20 characters")
    String username,

    @NotBlank(message = "Email is required")
    @Size(min = 1, max = 30, message = "Email must be between 1 and 30 characters")
    String email,

    @NotBlank(message = "Password is required")
    @Size(min = 1, max = 20, message = "Password must be between 1 and 20 characters")
    String password
){}
