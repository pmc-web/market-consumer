package com.pmc.market.domain.product.entity;

import com.pmc.market.domain.BaseTimeEntity;
import com.pmc.market.domain.user.entity.User;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Builder
public class FavoriteProduct extends BaseTimeEntity { // 상품 좋아요

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "favorite_product_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    public void likeProduct() {
        product.liked(this);
        user.likeProduct(this);
    }

    public void unlikeProduct() {
        product.unLiked(this);
        user.unLikeProduct(this);
    }

}
