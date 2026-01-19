package mat.shopProject.shop.project.controllers;

import mat.shopProject.shop.project.dto.OrderResponseDto;
import mat.shopProject.shop.project.models.Category;
import mat.shopProject.shop.project.models.Order;
import mat.shopProject.shop.project.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class OrderContoller {

    @Autowired
    OrderService orderService;

    @GetMapping("/api/v1/order")
    public ResponseEntity<List<OrderResponseDto>> getAllOrders(Authentication authentication){
        return ResponseEntity.ok(orderService.getAllUserOrders(authentication));
    }

    @PostMapping("/api/v1/order")
    public ResponseEntity<Order> createOrder(Authentication authentication){
        return ResponseEntity.ok(orderService.createOrder(authentication));
    }

    @GetMapping("/api/v1/order/{order_id}")
    public ResponseEntity<OrderResponseDto> getOrder(Authentication authentication, @PathVariable UUID order_id){
        return ResponseEntity.ok(orderService.getOrderById(authentication, order_id));
    }



}
