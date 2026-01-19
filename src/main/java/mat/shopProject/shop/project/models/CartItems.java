package mat.shopProject.shop.project.models;

import jakarta.persistence.*;
import lombok.*;
import mat.shopProject.shop.project.PK.CartItemId;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "cart_items")
public class CartItems {

    @EmbeddedId
    private CartItemId id;

    @Column(nullable = false, name = "quantity")
    private int quantity;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserModel user;

    @MapsId("productId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

}
