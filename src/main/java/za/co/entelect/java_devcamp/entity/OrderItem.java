package za.co.entelect.java_devcamp.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "OrderItem", schema = "public")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id", nullable = false)
    private Long orderItemId;

    @ManyToOne
    @JoinColumn(name = "ProductId")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "OrderId")
    private Order order;

    public OrderItem(Product product) {
        this.product = product;
    }
}
