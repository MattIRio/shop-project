package mat.shopProject.shop.project.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.*;
import mat.shopProject.shop.project.PK.CartItemId;
import mat.shopProject.shop.project.PK.OrderItemId;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "order_items")
public class OrderItems {

    @EmbeddedId
    private OrderItemId id;
    @Column(name = "quantity", nullable = false)
    @Positive(message = "Quantity must be greater than 0")
    private int quantity;
    @Column(name = "price_at_purchase", nullable = false)
    private BigDecimal price_at_purchase;

    @JsonIgnore
    @MapsId("orderId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @JsonIgnore
    @MapsId("productId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}
