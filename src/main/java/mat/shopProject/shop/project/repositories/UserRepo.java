package mat.shopProject.shop.project.repositories;

import mat.shopProject.shop.project.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepo extends JpaRepository<UserModel, UUID> {
    Optional<UserModel> findByUsername(String username);
    Optional<UserModel> findByEmail(String email);
    boolean existsByUsername(String username);


}
