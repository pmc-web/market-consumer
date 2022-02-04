package com.pmc.market.service;

import com.pmc.market.OrderApplication;
import com.pmc.market.model.dto.DeliveryUpdateRequestDto;
import com.pmc.market.domain.order.entity.DeliveryStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {OrderApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DeliveryServiceTest {
    @Autowired
    DeliveryService deliveryService;

    @Test
    void insertDelivery() {
        long orderId = 1L;
        deliveryService.insertDelivery(orderId);
    }

    @Test
    void updateStatus() {
        DeliveryUpdateRequestDto requestDto = DeliveryUpdateRequestDto.builder()
                .deliveryStatus(DeliveryStatus.SHIPPING)
                .shippingNumber("111111111")
                .build();
        deliveryService.updateStatus(1L, requestDto);
    }
}