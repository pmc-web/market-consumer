package com.pmc.market.model.shop.entity;

import com.pmc.market.model.user.entity.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime reg_date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id")
    // 다대일 관계에서 다 쪽이 외래키의 관리자가 되므로 연관관계의 주인이다. -> mapped By 설정 X
    private Shop shop;

    public void delete() {
        this.shop = null;
        this.user = null;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }
}
