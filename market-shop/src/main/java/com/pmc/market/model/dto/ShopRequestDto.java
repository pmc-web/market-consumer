package com.pmc.market.model.dto;

import com.pmc.market.domain.shop.entity.Category;
import com.pmc.market.domain.shop.entity.ShopImageType;
import com.pmc.market.domain.shop.entity.Shop;
import com.pmc.market.domain.user.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("Shop 생성, 수정시 필요한 ShopInput")
public class ShopRequestDto {
    @ApiModelProperty(value = "마켓 이름 ")
    @NotEmpty(message = "Shop Input(name) can not empty.")
    private String name;

    @ApiModelProperty(value = "기간", example = "1", notes = "마켓 을 오픈할 (년 단위) 기간 입니다.")
    @NotNull(message = "Shop Input(period) can not empty.")
    private int period;

    @ApiModelProperty(value = "마켓에 대한 긴 설명")
    @NotEmpty(message = "Shop Input(fullDescription) can not empty.")
    private String fullDescription;

    @ApiModelProperty(value = "마켓에 대한 짧은(한줄) 설명")
    @NotEmpty(message = "Shop Input(shortDescription) can not empty.")
    private String shortDescription;

    @ApiModelProperty(value = "사업자 번호")
    @NotEmpty(message = "Shop Input(businessNumber) can not empty.")
    private String businessNumber;

    @ApiModelProperty(value = "사업자 이름")
    @NotEmpty(message = "Shop Input(businessName) can not empty.")
    private String businessName;

    @ApiModelProperty(value = "별도 사업자명 ")
    @NotEmpty(message = "Shop Input(owner) can not empty.")
    private String owner;

    @ApiModelProperty(value = "마켓 관계자 연락처")
    @NotEmpty(message = "Shop Input(telephone) can not empty.")
    private String telephone;

    @ApiModelProperty(value = "배송비 정책 : 미입력시 무조건 유료배송, 0-전체 무료배송, 입력값-이상 무료배송, max:100,000,000 ", example = "30000", required = false)
    @Size(max = 100000000)
    private Integer deliveryCost;

    @ApiModelProperty(value = "QnA 정책")
    private String qnaDescription;

    @ApiModelProperty(value = "배송 정책")
    private String shipDescription;

    @ApiModelProperty(value = "카테고리 Id")
    private long categoryId;

    @ApiModelProperty(value = "이미지 타입")
    private ShopImageType shopImageType;

    public Shop toEntity(ShopRequestDto shopRequestDto, User user, Category category) {
        return Shop.builder()
                .name(shopRequestDto.getName())
                .period(LocalDateTime.now().plusYears(shopRequestDto.getPeriod()))
                .fullDescription(shopRequestDto.getFullDescription())
                .shortDescription(shopRequestDto.getShortDescription())
                .regDate(LocalDateTime.now())
                .businessName(shopRequestDto.getBusinessName())
                .businessNumber(shopRequestDto.getBusinessNumber())
                .owner(shopRequestDto.getOwner())
                .telephone(shopRequestDto.getTelephone())
                .user(user)
                .deliveryCost(shopRequestDto.deliveryCost)
                .qnaDescription(shopRequestDto.qnaDescription)
                .shipDescription(shopRequestDto.shipDescription)
                .category(category)
                .build();
    }

    public void updateShop(Shop shop) {
        shop.setName(this.name);
        shop.setPeriod(shop.getPeriod().plusYears(this.period));
        shop.setFullDescription(this.fullDescription);
        shop.setShortDescription(this.shortDescription);
        shop.setBusinessName(this.businessName);
        shop.setBusinessNumber(this.businessNumber);
        shop.setOwner(this.owner);
        shop.setTelephone(this.telephone);
        shop.setDeliveryCost(this.deliveryCost);
        shop.setQnaDescription(this.qnaDescription);
        shop.setShipDescription(this.shipDescription);
    }
}
