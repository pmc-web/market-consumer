package com.pmc.market.service;

import com.pmc.market.model.dto.OrderRequestDto;

public interface KakaoPayService {
    void orderKakaoPay();

    void requestOrder(OrderRequestDto orderRequestDto);

    void approve(String pgToken);
}
