package mat.shopProject.shop.project.repositories;

import mat.shopProject.shop.project.models.CartItems;
import mat.shopProject.shop.project.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CartItemsRepo extends JpaRepository<CartItems, UUID> {
    Optional<CartItems> findByUserIdAndProductId(UUID userId, UUID productId);
}
