package mat.shopProject.shop.project.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "product_id", nullable = false, updatable = false)
    private UUID id;
    @Column(name = "title", nullable = false)
    @Size(min = 1, max = 100, message = "Title name must be between 1 and 100 characters")
    private String title;
    @Column(name = "description", nullable = false)
    @Size(min = 1, max = 1000, message = "Description name must be between 1 and 1000 characters")
    private String description;
    @Column(name = "price", nullable = false)
    @Positive(message = "Price must be greater than 0")
    private BigDecimal price;
    @Column(name = "stockQuantity", nullable = false)
    private int stockQuantity;
    @Column(name = "isActive", nullable = false)
    private boolean isActive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    @JsonIgnore
    private Category category;

}
