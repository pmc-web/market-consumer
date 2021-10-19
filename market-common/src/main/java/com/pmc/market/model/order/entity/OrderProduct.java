package com.pmc.market.model.order.entity;

import com.pmc.market.model.product.entity.Product;
import com.pmc.market.model.product.entity.Review;
import lombok.*;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Entity
public class OrderProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_id")
    private Purchase purchase;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @OneToOne
    @JoinColumn(name = "review_id")
    private Review review;

    private String size;
    private String color;
    private Integer price;
    private Integer quantity;

    public void setReview(Review review) {
        this.review = review;
    }
}
