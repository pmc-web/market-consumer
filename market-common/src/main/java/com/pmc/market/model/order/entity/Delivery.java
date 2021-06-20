package com.pmc.market.model.order.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Entity
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private DeliveryStatus status;

    @Column
    private String shippingNumber;

    @OneToOne(mappedBy = "delivery")
    private Purchase purchase;
}
