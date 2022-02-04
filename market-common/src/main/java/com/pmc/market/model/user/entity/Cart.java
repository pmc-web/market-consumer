package com.pmc.market.model.user.entity;

import com.pmc.market.error.exception.BusinessException;
import com.pmc.market.error.exception.ErrorCode;
import com.pmc.market.model.BaseTimeEntity;
import com.pmc.market.model.shop.entity.Shop;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Cart extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // 다대일 양방향
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cart", cascade = CascadeType.ALL)
    private List<CartProduct> products = new ArrayList<>();

    public void removeProduct(CartProduct product) {
        if (products.size() == 0) {
            throw new BusinessException("이미 상품이 없습니다.", ErrorCode.INVALID_INPUT_VALUE);
        }
        products.remove(product);
    }

    public void addProduct(CartProduct product) {
        products.add(product);
    }
}
