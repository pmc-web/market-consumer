package com.pmc.market.service;

import com.pmc.market.model.dto.DeliveryUpdateRequestDto;

public interface DeliveryService {
    void insertDelivery(long orderId);

    void updateStatus(long deliveryId, DeliveryUpdateRequestDto deliveryRequest);
}
