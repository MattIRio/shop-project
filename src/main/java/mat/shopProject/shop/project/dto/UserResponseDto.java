package mat.shopProject.shop.project.dto;



import mat.shopProject.shop.project.models.enums.Role;

import java.util.UUID;

public record UserResponseDto(UUID id, String username, Role role) {

}
