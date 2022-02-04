package com.pmc.market.model.user.entity;

import com.pmc.market.model.BaseTimeEntity;
import com.pmc.market.model.product.entity.Product;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class CartProduct extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_product_id")
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @NotNull
    private int quantity;

    @NotNull
    private Integer totalPrice;

    private String size;

    private String color;

    public void updateCartProductQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void updateCartProductSize(String size) {
        this.size = size;
    }

    public void updateCartProductColor(String color) {
        this.color = color;
    }
}
