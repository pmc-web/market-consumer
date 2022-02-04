package com.pmc.market.model.vo.kakao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CancelAvailableAmountVo {
    private Integer total, tax_free, vat, point, discount;
}
