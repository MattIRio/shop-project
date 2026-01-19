package mat.shopProject.shop.project.dto;

import lombok.Builder;
import mat.shopProject.shop.project.models.Product;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record CartItemDto(
        int quantity,
        String title,
        String description,
        BigDecimal price,
        Integer stockQuantity
) {}
