package com.pmc.market.model.dto;

import com.pmc.market.domain.order.entity.Delivery;
import com.pmc.market.domain.order.entity.DeliveryStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@ApiModel("배송시작일떄 필요한 정보")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class DeliveryUpdateRequestDto {
    @NotNull
    @ApiModelProperty("배송상태")
    private DeliveryStatus deliveryStatus;

    @ApiModelProperty("운송번호")
    private String shippingNumber;

    public void updateStatus(Delivery delivery) {
        if (shippingNumber != null) delivery.updateShippingNumber(shippingNumber);
        delivery.updateStatus(deliveryStatus);
    }

}
