package com.pmc.market.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ShopDto {
    private Long id;
    private String name;
    private LocalDateTime period;
    private String fullDescription;
    private String shortDescription;
    private LocalDateTime regDate;
    private String businessNumber;
    private String businessName;
    private String owner;
    private String telephone;
}
