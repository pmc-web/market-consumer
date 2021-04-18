package com.pmc.market.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ShopInput {
    private String name;
    private int period;
    private String fullDescription;
    private String shortDescription;
    private String businessNumber;
    private String businessName;
    private String owner;
    private String telephone;
}
