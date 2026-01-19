package mat.shopProject.shop.project.mapper;

import mat.shopProject.shop.project.dto.OrderResponseDto;
import mat.shopProject.shop.project.models.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public OrderResponseDto toDto(Order order) {
        return new OrderResponseDto(
                order.getStatus(),
                order.getOrderItems(),
                order.getTotal_amount(),
                order.getCreated_at()
        );
    }
}
