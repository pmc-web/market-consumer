package com.pmc.market.model.order.entity;

import com.pmc.market.model.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Entity
public class Delivery extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    private String shippingNumber;

    @OneToOne(mappedBy = "delivery")
    private Order order;

    public void updateStatus(DeliveryStatus status) {
        this.status = status;
    }

    public void updateShippingNumber(String shippingNumber) {
        this.shippingNumber = shippingNumber;
    }
}
