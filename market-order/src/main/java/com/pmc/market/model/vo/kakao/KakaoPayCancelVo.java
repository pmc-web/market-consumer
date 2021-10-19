package com.pmc.market.model.vo.kakao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KakaoPayCancelVo {
    private String aid, tid, cid, sid;
    private String status;
    private String partner_order_id, partner_user_id, payment_method_type;
    private AmountVo amount;
    private ApprovedCancelAmountVo approved_cancel_amount;
    private CanceledAmountVo canceled_amount;
    private CancelAvailableAmountVo cancel_available_amount;
    private String item_name, item_code, payload;
    private Integer quantity;
    private LocalDateTime created_at, approved_at, canceled_at;
}
