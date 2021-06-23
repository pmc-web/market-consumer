package com.pmc.market.service;

import com.pmc.market.model.dto.OrderRequestDto;
import com.pmc.market.model.user.entity.User;
import com.pmc.market.model.vo.OrderResponseVo;

import java.util.List;

public interface OrderService {
    void makeOrder(OrderRequestDto orderRequestDto);

    List<OrderResponseVo> getUserOrderList(User user);

    OrderResponseVo getOrder(long orderId);
}

