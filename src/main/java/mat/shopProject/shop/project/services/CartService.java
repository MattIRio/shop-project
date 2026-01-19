package mat.shopProject.shop.project.services;

import jakarta.transaction.Transactional;
import mat.shopProject.shop.project.PK.CartItemId;
import mat.shopProject.shop.project.dto.CartItemDto;
import mat.shopProject.shop.project.exception.InsufficientStockException;
import mat.shopProject.shop.project.models.AuthenticatedUser;
import mat.shopProject.shop.project.models.CartItems;
import mat.shopProject.shop.project.models.Product;
import mat.shopProject.shop.project.models.UserModel;
import mat.shopProject.shop.project.repositories.CartItemsRepo;
import mat.shopProject.shop.project.repositories.ProductRepo;
import mat.shopProject.shop.project.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Transactional
@Service
public class CartService {

    @Autowired
    CartItemsRepo cartItemsRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    ProductRepo productRepo;

    public CartItemDto addProductToCart(UUID productId, int quantity, Authentication authentication) {
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Product with id " + productId + " not found"
                ));
        if (product.getStockQuantity() < quantity) {
            throw new InsufficientStockException(
                    "Not enough products in storage : " + product.getStockQuantity() +
                            ", requested: " + quantity
            );
        }
        UserModel user = userRepo.findById(((AuthenticatedUser) authentication.getPrincipal()).getId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User with such id not found"
                ));
        CartItems cartItems = CartItems.builder()
                .id(new CartItemId(user.getId(), productId))
                .product(product)
                .user(user)
                .quantity(quantity)
                .build();

        cartItemsRepo.save(cartItems);

        CartItemDto cartItemDto = CartItemDto.builder()
                .price(product.getPrice())
                .title(product.getTitle())
                .description(product.getDescription())
                .stockQuantity(product.getStockQuantity())
                .quantity(quantity)
                .build();
        return cartItemDto;
    }

    public List<CartItemDto> getProductsFromCart(Authentication authentication) {
        UUID userId = ((AuthenticatedUser) authentication.getPrincipal()).getId();
        return userRepo.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"))
                .getCartItems()
                .stream()
                .map(cartItem -> new CartItemDto(
                        cartItem.getQuantity(),
                        cartItem.getProduct().getTitle(),
                        cartItem.getProduct().getDescription(),
                        cartItem.getProduct().getPrice(),
                        cartItem.getProduct().getStockQuantity()
                ))
                .toList();
    }

    public CartItemDto changeProductQuantity(UUID productId, int quantity, Authentication authentication) {
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Product with id " + productId + " not found"
                ));
        if (product.getStockQuantity() < quantity) {
            throw new InsufficientStockException(
                    "Not enough products in storage : " + product.getStockQuantity() +
                            ", requested: " + quantity
            );
        }
        UUID userId = ((AuthenticatedUser) authentication.getPrincipal()).getId();
        CartItems cartItems = cartItemsRepo.findByUserIdAndProductId(userId, productId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Cart item not found"
                ));
        cartItems.setQuantity(quantity);
        cartItemsRepo.save(cartItems);

        CartItemDto cartItemDto = CartItemDto.builder()
                .price(cartItems.getProduct().getPrice())
                .title(cartItems.getProduct().getTitle())
                .description(cartItems.getProduct().getDescription())
                .stockQuantity(cartItems.getProduct().getStockQuantity())
                .quantity(quantity)
                .build();

        return (cartItemDto);
    }

    public void deleteProductFromCart(UUID productId, Authentication authentication) {
        UUID userId = ((AuthenticatedUser) authentication.getPrincipal()).getId();
        CartItems cartItems = cartItemsRepo.findByUserIdAndProductId(userId, productId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Cart item not found"
                ));
        cartItemsRepo.delete(cartItems);
    }
}
