package com.pmc.market.model.dto;

import com.pmc.market.entity.User;
import com.pmc.market.model.entity.Shop;
import io.swagger.annotations.ApiParam;
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

    @ApiParam(name = "name", value ="쇼핑몰 이름")
    @NotEmpty(message = "Shop Input(name) is not empty.")
    private String name;

    @ApiParam(name = "period", value ="쇼핑몰 기간(년 단위)", example = "1")
    @NotNull(message = "Shop Input(period) is not empty.")
    private int period;

    @ApiParam(name = "fullDescription", value ="상세한 마켓 소개")
    @NotEmpty(message = "Shop Input(fullDescription) is not empty.") // 정책 필요
    @Lob // 긴 문자열
    private String fullDescription;

    @ApiParam(name = "shortDescription", value ="짧은 마켓 소개")
    @NotEmpty(message = "Shop Input(shortDescription) is not empty.")
    private String shortDescription;

    @ApiParam(name = "businessNumber", value ="사업자 번호")
    @NotEmpty(message = "Shop Input(businessNumber) is not empty.")
    private String businessNumber;

    @ApiParam(name = "businessName", value ="사업자이름")
    @NotEmpty(message = "Shop Input(businessName) is not empty.")
    private String businessName;

    @ApiParam(name = "owner", value ="마켓 생성하는 유저의 이메일")
    @NotEmpty(message = "Shop Input(owner) is not empty.")
    private String owner;

    @ApiParam(name = "telephone", value ="마켓 관계자 연락처")
    @NotEmpty(message = "Shop Input(telephone) is not empty.")
    private String telephone;


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
                .build();
    }
}
