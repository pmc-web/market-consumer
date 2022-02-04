package com.pmc.market.model.order.entity;

import com.pmc.market.model.BaseTimeEntity;
import com.pmc.market.model.product.entity.Product;
import com.pmc.market.model.product.entity.Review;
import lombok.*;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Entity
public class OrderProduct extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_product_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_id")
    private Order order;

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
