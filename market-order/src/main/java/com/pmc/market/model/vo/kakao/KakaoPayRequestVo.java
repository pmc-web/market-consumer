package com.pmc.market.model.vo.kakao;

import com.pmc.market.model.order.entity.Order;
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

    public static KakaoPayRequestVo from(Order order) {
        String itemName = order.getProducts().get(0).getProduct().getName();
        if (order.getProducts().size() > 0) itemName += "외 " + order.getProducts().size() + " 건";
        return KakaoPayRequestVo.builder()
                .partnerOrderId(String.valueOf(order.getId())) // 주문 번호
                .partnerUserId(String.valueOf(order.getUser().getId()))
                .itemName(itemName)
                .itemCode("MARKET" + order.getShop().getId() + "_" + order.getId())
                .quantity(order.getAmount())
                .totalAmount(order.getTotalPrice())
                .taxFreeAmount(0)
                .build();
    }
}
