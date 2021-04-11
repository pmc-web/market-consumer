package com.pmc.market.entity;

import lombok.Getter;

@Getter
public enum DeliveryStatus {
    READY("배송 준비", 0), SHIPPING("배송 중", 1), COMPLETED("배송 완료", 2);

    private String deliveryStatus;
    private int delivery;

    DeliveryStatus(String deliveryStatus, int delivery) {
        this.deliveryStatus = deliveryStatus;
        this.delivery = delivery;
    }
}