package com.pmc.market.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KakaoPayReadyVo {
    private final String cid = "TC0ONETIME"; // 가맹점 코드
    private String partnerOrderId; // 가맹점
    private String partner_user_id;
    private String item_name;
    private String item_code;
    private Integer quantity;
    private Integer total_amount;

    private Integer tax_free_amount;
    private String approval_url;
    private String cancel_url;
    private String fail_url;


}
