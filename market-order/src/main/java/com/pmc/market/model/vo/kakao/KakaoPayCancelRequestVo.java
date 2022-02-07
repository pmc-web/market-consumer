package com.pmc.market.model.vo.kakao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KakaoPayCancelRequestVo {
    private String cid; // 가맹점 코드
    private String tid;
    private Integer cancelAmount;
    private Integer cancelTaxFreeAmount;
}
