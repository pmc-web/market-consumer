package com.pmc.market.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.math.BigInteger;
import java.sql.Timestamp;
@ToString
@Getter
@AllArgsConstructor
public class ShopLikesDto {
    private BigInteger id;
    private String name;
    private Timestamp period;
    private String fullDescription;
    private String shortDescription;
    private Timestamp regDate;
    private String businessNumber;
    private String businessName;
    private String owner;
    private String telephone;
    private BigInteger categoryId;
    private BigInteger userId;
    private Integer deliveryCost;
    private String qnaDescription;
    private String shipDescription;
    private BigInteger likes;
}
