package mat.shopProject.shop.project.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import mat.shopProject.shop.project.dto.CreateUserRequest;
import mat.shopProject.shop.project.dto.UserResponseDto;
import mat.shopProject.shop.project.exception.UserAlreadyExistsException;
import mat.shopProject.shop.project.models.UserModel;
import mat.shopProject.shop.project.models.enums.Role;
import mat.shopProject.shop.project.repositories.UserRepo;
import mat.shopProject.shop.project.securityConfig.JWTService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
@Transactional
public class AuthService {

    private final UserRepo userRepo;

    private final JWTService jwtService;

    private final AuthenticationManager authManager;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public UserResponseDto register(CreateUserRequest userRequest) {

        UserModel user = UserModel.builder()
                .username(userRequest.username())
                .email(userRequest.email())
                .created_at(LocalDateTime.now())
                .password(userRequest.password())
                .role(Role.USER)
                .build();

        String username = user.getUsername();
        String password = encoder.encode(user.getPassword());
        if (userRepo.existsByUsername(username)) {
            throw new UserAlreadyExistsException("Username already taken");
        }

        user.setPassword(password);
        userRepo.save(user);
        return new UserResponseDto(
                user.getId(),
                user.getUsername(),
                user.getRole()
        );
    }
}
