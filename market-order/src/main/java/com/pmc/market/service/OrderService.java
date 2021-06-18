package com.pmc.market.service;

import com.pmc.market.model.dto.OrderRequestDto;

public interface OrderService {
    void makeOrder(OrderRequestDto orderRequestDto);
}
