package com.pmc.market.model.dto;

import com.pmc.market.entity.ShipAddress;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ShipAddressResponseDto {
    private Long id;

    @ApiModelProperty(value = "전체 주소")
    private String address;

    @ApiModelProperty(value = "상세 주소")
    private String detail;

    @ApiModelProperty(value = "우편 번호")
    private String zipCode;

    @ApiModelProperty(value = "주소 이름")
    private String addressName;

    public static ShipAddressResponseDto from(ShipAddress shipAddress) {
        return ShipAddressResponseDto.builder()
                .id(shipAddress.getId())
                .address(shipAddress.getAddress())
                .detail(shipAddress.getDetail())
                .zipCode(shipAddress.getZipCode())
                .addressName(shipAddress.getAddressName())
                .build();
    }
}
