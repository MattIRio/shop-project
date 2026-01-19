package mat.shopProject.shop.project.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.*;
import mat.shopProject.shop.project.models.enums.Status;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "order_id", nullable = false, updatable = false)
    private UUID id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItems> orderItems;

    @Column(name = "status", nullable = false)
    private Status status;
    @Column(name = "total_amount", nullable = false)
    @Positive(message = "Amount must be greater than 0")
    private BigDecimal total_amount;
    @Column(name = "created_at", nullable = false)
    private LocalDateTime created_at;
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updated_at;
}
