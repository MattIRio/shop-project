package mat.shopProject.shop.project.dto;

public record ProductDto(
        String title,
        String description,
        String price,
        String stockQuantity
) {
}
