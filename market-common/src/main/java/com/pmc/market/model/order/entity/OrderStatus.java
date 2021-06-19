package com.pmc.market.model.order.entity;

import lombok.Getter;

@Getter
public enum OrderStatus {
    ORDER_CHECKING("주문 확인", 0),
    DEPOSIT_COMPLETE("입금 완료", 1), DEPOSIT_CHECKING("입금 확인", 2),
    PAYMENT_COMPLETE("결제 완료", 2),
    DELIVERY_READY("배송 준비", 3), DELIVERY_COMPLETE("배송 완료", 4),
    REFUND("환불중", 5),
    ORDER_COMPLETE("거래 완료", 6);


    private String statusName;
    private int order;

    OrderStatus(String statusName, int order) {
        this.statusName = statusName;
        this.order = order;
    }
}
