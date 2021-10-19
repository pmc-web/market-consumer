package com.pmc.market.model.dto;

import com.pmc.market.model.user.entity.ShipAddress;
import com.pmc.market.model.user.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
@Builder
@AllArgsConstructor
@ApiModel("배송지 추가, 수정")
public class ShipAddressRequestDto {

    @ApiModelProperty(value = "전체 주소")
    @NotEmpty
    private String address;

    @ApiModelProperty(value = "상세 주소")
    @NotEmpty
    private String detail;

    @ApiModelProperty(value = "우편 번호")
    @NotEmpty
    private String zipCode;

    @ApiModelProperty(value = "주소 이름")
    private String addressName;

    @ApiModelProperty(value = "기본 주소 설정 여부")
    private Boolean isDefault;

    public ShipAddress toEntity(ShipAddressRequestDto request, User user) {
        return ShipAddress.builder()
                .address(request.getAddress())
                .detail(request.getDetail())
                .zipCode(request.getZipCode())
                .user(user)
                .isDefault(request.getIsDefault())
                .build();
    }
}
