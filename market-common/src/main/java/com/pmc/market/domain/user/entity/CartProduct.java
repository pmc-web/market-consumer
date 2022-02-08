package com.pmc.market.domain.user.entity;

import com.pmc.market.domain.BaseTimeEntity;
import com.pmc.market.domain.product.entity.Product;
import com.pmc.market.domain.shop.dto.CartProductRequestDto;
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

    public void updateCart(CartProductRequestDto cartProduct) {
        this.color = cartProduct.getColor();
        this.size = cartProduct.getSize();
        this.quantity = cartProduct.getQuantity();
    }

    public void addCart() {
        cart.addProduct(this);
    }

    public void removeCart() {
        cart.removeProduct(this);
    }
}
