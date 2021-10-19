package com.pmc.market.model.vo.kakao;

import com.pmc.market.model.order.entity.Purchase;
import lombok.*;

@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KakaoPayRequestVo {
    private String cid = "TC0ONETIME"; // 가맹점 코드
    private String partnerOrderId; // 가맹점
    private String partnerUserId;
    private String itemName;
    private String itemCode;
    private Integer quantity;
    private Integer totalAmount;
    private Integer taxFreeAmount;

    public static KakaoPayRequestVo from(Purchase purchase) {
        String itemName = purchase.getProducts().get(0).getProduct().getName();
        if (purchase.getProducts().size() > 0) itemName += "외 " + purchase.getProducts().size() + " 건";
        return KakaoPayRequestVo.builder()
                .partnerOrderId(String.valueOf(purchase.getId())) // 주문 번호
                .partnerUserId(String.valueOf(purchase.getUser().getId()))
                .itemName(itemName)
                .itemCode("MARKET" + purchase.getShop().getId() + "_" + purchase.getId())
                .quantity(purchase.getAmount())
                .totalAmount(purchase.getTotalPrice())
                .taxFreeAmount(0)
                .build();
    }
}
