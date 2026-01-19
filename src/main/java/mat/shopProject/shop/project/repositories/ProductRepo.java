package mat.shopProject.shop.project.repositories;

import mat.shopProject.shop.project.models.Product;
import mat.shopProject.shop.project.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepo extends JpaRepository<Product, UUID> {
}


