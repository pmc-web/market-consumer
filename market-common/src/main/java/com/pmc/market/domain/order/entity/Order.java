package com.pmc.market.domain.order.entity;

import com.pmc.market.domain.BaseTimeEntity;
import com.pmc.market.domain.shop.entity.Shop;
import com.pmc.market.domain.user.entity.User;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@Getter
@AllArgsConstructor
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @NotNull
    private String shipAddress;

    @NotNull
    private String zipCode;

    @Enumerated(EnumType.STRING)
    private Pay pay; // 결제 방법

    private String payInfo; // 결제 id 정보

    @NotNull
    private int amount;

    @NotNull
    private Integer totalPrice;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @NotNull
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderProduct> products = new ArrayList<>();

    public void updateProducts(List<OrderProduct> orderProducts) {
        this.products = orderProducts;
    }

    public void updateStatus(OrderStatus status) {
        this.status = status;
    }

    public void cancel() {
        this.totalPrice = 0;
        this.status = OrderStatus.REFUND;
    }
}
