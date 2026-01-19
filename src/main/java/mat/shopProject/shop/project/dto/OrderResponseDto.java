package mat.shopProject.shop.project.dto;

import lombok.Builder;
import mat.shopProject.shop.project.models.OrderItems;
import mat.shopProject.shop.project.models.enums.Status;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
public record OrderResponseDto(
        Status status,
        List<OrderItems> orderItems,
        BigDecimal total_amount,
        LocalDateTime created_at
) {
}
