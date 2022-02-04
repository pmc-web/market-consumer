package com.pmc.market.domain.order.entity;

import lombok.Getter;

@Getter
public enum Pay {
    BANK("무통장 입금"), KAKAO_PAY("카카오 페이");

    private String payName;

    Pay(String payName) {
        this.payName = payName;
    }

}
