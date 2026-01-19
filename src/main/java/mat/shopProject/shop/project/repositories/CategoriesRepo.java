package mat.shopProject.shop.project.repositories;

import mat.shopProject.shop.project.models.Category;
import mat.shopProject.shop.project.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoriesRepo extends JpaRepository<Category, UUID> {

}
