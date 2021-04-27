package com.pmc.market.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Lob;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ShopInput {

    @NotEmpty(message = "Shop Input(name) is not empty.")
    private String name;

    @NotNull(message = "Shop Input(period) is not empty.")
    private int period;

    @NotEmpty(message = "Shop Input(fullDescription) is not empty.") // 정책 필요
    @Lob // 긴 문자열
    private String fullDescription;

    @NotEmpty(message = "Shop Input(shortDescription) is not empty.")
    private String shortDescription;

    @NotEmpty(message = "Shop Input(businessNumber) is not empty.")
    private String businessNumber;

    @NotEmpty(message = "Shop Input(businessName) is not empty.")
    private String businessName;

    @NotEmpty(message = "Shop Input(owner) is not empty.")
    private String owner;

    @NotEmpty(message = "Shop Input(telephone) is not empty.")
    private String telephone;
}
