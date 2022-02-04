package com.pmc.market.service;

import com.pmc.market.error.exception.EntityNotFoundException;
import com.pmc.market.model.dto.DeliveryUpdateRequestDto;
import com.pmc.market.model.order.entity.Delivery;
import com.pmc.market.model.order.entity.DeliveryStatus;
import com.pmc.market.model.order.entity.Order;
import com.pmc.market.repository.DeliveryRepository;
import com.pmc.market.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class DeliveryServiceImpl implements DeliveryService {

    private final OrderRepository orderRepository;
    private final DeliveryRepository deliveryRepository;

    @Override
    public void insertDelivery(long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("구매 이력이 없습니다."));
        Delivery delivery = Delivery.builder()
                .order(order)
                .status(DeliveryStatus.READY)
                .build();
        deliveryRepository.save(delivery);
    }

    @Transactional
    @Override
    public void updateStatus(long deliveryId, DeliveryUpdateRequestDto deliveryUpdateReq) {
        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new EntityNotFoundException("배송 정보가 없습니다."));
        deliveryUpdateReq.updateStatus(delivery);
    }
}
