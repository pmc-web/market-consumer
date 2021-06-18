package com.pmc.market.model.vo;

import lombok.Getter;

@Getter
public class KakaoPayRequestVo {
    private final String cid = "TC0ONETIME"; // 가맹점 코드
    private String partnerOrderId; // 가맹점
    private String partnerUserId;
    private String itemName;
    private String itemCode;
    private Integer quantity;
    private Integer totalAmount;
    private Integer taxFreeAmount;
    private String approvalUrl;
    private String cancelUrl;
    private String failUrl;
}
