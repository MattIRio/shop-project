package mat.shopProject.shop.project.controllers;

import mat.shopProject.shop.project.dto.CartItemDto;
import mat.shopProject.shop.project.models.CartItems;
import mat.shopProject.shop.project.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class CartController {

    @Autowired
    CartService cartService;

    @PostMapping("/api/v1/cart/{quantity}/{productId}")
    public ResponseEntity<CartItemDto> addItemToCart(@PathVariable Integer quantity, @PathVariable UUID productId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(cartService.addProductToCart(productId, quantity, authentication));
    }

    @GetMapping("/api/v1/cart")
    public ResponseEntity<List<CartItemDto>> getCartItems(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(cartService.getProductsFromCart(authentication));
    }

    @PutMapping("/api/v1/cart/{quantity}/{productId}")
    public ResponseEntity<CartItemDto> changeProductQuantity(@PathVariable Integer quantity, @PathVariable UUID productId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(cartService.changeProductQuantity(productId, quantity, authentication));
    }

    @DeleteMapping("/api/v1/cart/{productId}")
    public ResponseEntity<String> deleteProductFromCart(@PathVariable UUID productId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        cartService.deleteProductFromCart(productId, authentication);
        return ResponseEntity.noContent().build();
    }
}
