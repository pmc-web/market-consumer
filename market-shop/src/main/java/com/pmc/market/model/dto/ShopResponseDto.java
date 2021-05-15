package com.pmc.market.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ShopResponseDto {
    // TODO: 좋아요 정보도 넣어야 하는데 ????
    private long id;
    private String name;
    private LocalDateTime period;
    private String fullDescription;
    private String shortDescription;
    private LocalDateTime regDate;
    private String businessNumber;
    private String businessName;
    private String owner;
    private String telephone;
    private Long categoryId;
    private Long userId;
    private Integer deliveryCost;
    private String qnaDescription;
    private String shipDescription;
}
