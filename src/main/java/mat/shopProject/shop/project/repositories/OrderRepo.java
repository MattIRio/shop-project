package mat.shopProject.shop.project.repositories;

import mat.shopProject.shop.project.models.CartItems;
import mat.shopProject.shop.project.models.Order;
import mat.shopProject.shop.project.models.UserModel;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OrderRepo extends JpaRepository<Order, UUID> {
    Optional<Order> findByIdAndUserId (UUID userId, UUID id);
}
