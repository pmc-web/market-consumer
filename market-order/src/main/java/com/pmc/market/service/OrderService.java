package com.pmc.market.service;

import com.pmc.market.model.dto.OrderRequestDto;
import com.pmc.market.domain.order.entity.OrderStatus;
import com.pmc.market.domain.user.entity.User;
import com.pmc.market.model.vo.OrderResponseVo;

import java.util.List;

public interface OrderService {
    void makeOrder(OrderRequestDto orderRequestDto, User user);

    List<OrderResponseVo> getUserOrderList(User user);

    OrderResponseVo getOrder(long orderId);

    void updateState(long orderId, OrderStatus status);

    String cancelOrder(long orderId);

    void makeOrder(OrderRequestDto request);
}

