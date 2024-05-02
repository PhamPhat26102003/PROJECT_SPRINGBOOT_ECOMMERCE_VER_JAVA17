package projectspringboot.library.model;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cart_item")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cartItem_id")
    private Long id;
    private int quantity;
    private double totalPrice;
    private double unitPrice;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "shoppingCart_id", referencedColumnName = "shoppingCart_id")
    private ShoppingCart cart;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private Laptop laptop;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "accessory_id", referencedColumnName = "accessory_id")
    private Accessories accessories;

}
