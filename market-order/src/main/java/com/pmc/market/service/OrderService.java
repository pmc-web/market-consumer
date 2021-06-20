package com.pmc.market.service;

import com.pmc.market.model.dto.OrderRequestDto;
import com.pmc.market.model.dto.OrderResponseDto;
import com.pmc.market.model.user.entity.User;

import java.util.List;

public interface OrderService {
    void makeOrder(OrderRequestDto orderRequestDto);

    List<OrderResponseDto> getUserOrderList(User user);

    OrderResponseDto getOrder(long orderId);
}

