package com.pmc.market.model.dto;

import com.pmc.market.entity.User;
import com.pmc.market.model.entity.Category;
import com.pmc.market.model.entity.Shop;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Lob;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

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

    private Integer deliveryCost;
    private String qnaDescription;
    private String shipDescription;
    private long categoryId;

    public Shop toEntity(ShopInput shopInput, User user) {
        return Shop.builder()
                .name(shopInput.getName())
                .period(LocalDateTime.now().plusYears(shopInput.getPeriod()))
                .fullDescription(shopInput.getFullDescription())
                .shortDescription(shopInput.getShortDescription())
                .regDate(LocalDateTime.now())
                .businessName(shopInput.getBusinessName())
                .businessNumber(shopInput.getBusinessNumber())
                .owner(shopInput.getOwner())
                .telephone(shopInput.getTelephone())
                .user(user)
                .deliveryCost(shopInput.deliveryCost)
                .shipDescription(shopInput.qnaDescription)
                .shipDescription(shopInput.shipDescription)
//                .category() TODO : category id 도 받아야 함..
                .build();
    }
}
