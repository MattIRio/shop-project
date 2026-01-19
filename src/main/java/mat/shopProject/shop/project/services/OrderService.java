package mat.shopProject.shop.project.services;

import jakarta.transaction.Transactional;
import mat.shopProject.shop.project.PK.OrderItemId;
import mat.shopProject.shop.project.dto.OrderResponseDto;
import mat.shopProject.shop.project.mapper.OrderMapper;
import mat.shopProject.shop.project.models.*;
import mat.shopProject.shop.project.models.enums.Status;
import mat.shopProject.shop.project.repositories.CartItemsRepo;
import mat.shopProject.shop.project.repositories.OrderRepo;
import mat.shopProject.shop.project.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class OrderService {

    @Autowired
    OrderRepo orderRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    CartItemsRepo cartItemsRepo;

    public List<OrderResponseDto> getAllUserOrders(Authentication authentication) {
        UUID userId = ((AuthenticatedUser) authentication.getPrincipal()).getId();
        UserModel user = userRepo.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        List<Order> userOrders = user.getOrders();
        List<OrderResponseDto> userOrdersResponse = new ArrayList<>();

        for (Order order : userOrders){
            userOrdersResponse.add(orderMapper.toDto(order));
        }

        return userOrdersResponse;
    }

    public OrderResponseDto getOrderById(Authentication authentication, UUID orderId) {
        UUID userId = ((AuthenticatedUser) authentication.getPrincipal()).getId();

        UserModel user = userRepo.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        Order order = orderRepo.findByIdAndUserId(orderId, userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));

        OrderResponseDto orderResponse = OrderResponseDto.builder()
                .orderItems(order.getOrderItems())
                .total_amount(order.getTotal_amount())
                .status(order.getStatus())
                .created_at(order.getCreated_at())
                .build();

        return orderResponse;
    }

    public Order createOrder(Authentication authentication) {
        UUID userId = ((AuthenticatedUser) authentication.getPrincipal()).getId();
        UserModel user = userRepo.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        List<CartItems> userCartItems = user.getCartItems();
        if (userCartItems.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart is empty");
        }
        List<OrderItems> userOrderItems = new ArrayList<>();
        List<Order> userOrders = user.getOrders();
        Order order = Order.builder()
                .created_at(LocalDateTime.now())
                .status(Status.PENDING)
                .updated_at(LocalDateTime.now())
                .user(user)
                .build();
        order.setOrderItems(userOrderItems);
        order.setTotal_amount(countTotalAmount(userCartItems));
        orderRepo.save(order);

        for (CartItems cartItem : userCartItems){
            OrderItems orderItems = OrderItems.builder()
                    .id(new OrderItemId(order.getId(), cartItem.getProduct().getId()))
                    .order(order)
                    .quantity(cartItem.getQuantity())
                    .product(cartItem.getProduct())
                    .price_at_purchase(cartItem.getProduct().getPrice())
                    .build();
            userOrderItems.add(orderItems);
        }

        user.getCartItems().clear();
        cartItemsRepo.deleteAll(user.getCartItems());
        userOrders.add(order);
        user.setOrders(userOrders);
        userRepo.save(user);

        return order;
    }

    public BigDecimal countTotalAmount(List<CartItems> cartItems){
        BigDecimal totalAmount = BigDecimal.valueOf(0);
        for (CartItems cartItem : cartItems){
            totalAmount = totalAmount.add(cartItem.getProduct().getPrice());
        }
        return totalAmount;
    }
}
