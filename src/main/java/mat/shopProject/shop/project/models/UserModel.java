package mat.shopProject.shop.project.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import mat.shopProject.shop.project.models.enums.Role;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id", nullable = false, updatable = false)
    private UUID id;
    @Column(name = "username", nullable = false)
    @Size(min = 1, max = 100, message = "Title name must be between 1 and 100 characters")
    private String username;
    @Column(name = "email", nullable = false)
    @Size(min = 1, max = 100, message = "Email name must be between 1 and 100 characters")
    private String email;
    @Column(name = "password", nullable = false)
    @Size(min = 1, max = 30, message = "Password name must be between 1 and 30 characters")
    private String password;
    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(name = "created_at", nullable = false)
    private LocalDateTime created_at;

    @OneToMany(mappedBy = "user", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<>();;

    @JsonIgnore
    @OneToMany(mappedBy = "user", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CartItems> cartItems;
}
