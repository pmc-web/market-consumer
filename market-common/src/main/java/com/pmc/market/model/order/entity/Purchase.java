package com.pmc.market.model.order.entity;

import com.pmc.market.model.shop.entity.Shop;
import com.pmc.market.model.user.entity.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Purchase { // ORDER 가 예약어기 때문에 변경
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String address;

    private String zipCode;

    private Integer payment;

    private Integer amount;

    private Integer totalPrice;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private LocalDateTime regDate;

    private LocalDateTime updateDate;

    private Integer shipCost;

    @OneToOne
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // TODO : order product 말고 cart product 로 해야하는 거 아닌가?
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "purchase", cascade = CascadeType.ALL)
    private List<OrderProduct> products = new ArrayList<>();
}
