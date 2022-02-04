package com.pmc.market.domain.shop.entity;

import com.pmc.market.domain.BaseTimeEntity;
import com.pmc.market.domain.user.entity.User;
import lombok.*;

import javax.persistence.*;

@Builder
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Favorite extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "favorite_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id")
    // 다대일 관계에서 다 쪽이 외래키의 관리자가 되므로 연관관계의 주인이다. -> mapped By 설정 X
    private Shop shop;

    public void likeShop() {
        shop.liked(this);
        user.like(this);
    }

    public void unlikeShop() {
        shop.unLiked(this);
        user.unLike(this);
    }

}
