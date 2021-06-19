package com.pmc.market.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApprovedCancelAmountVo {
    private Integer total, tax_free, vat, point, discount;
}
