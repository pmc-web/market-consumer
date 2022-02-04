package com.pmc.market.service;

import com.pmc.market.OrderApplication;
import com.pmc.market.model.dto.OrderRequestDto;
import com.pmc.market.model.dto.ProductRequestDto;
import com.pmc.market.domain.order.entity.Pay;
import com.pmc.market.domain.user.entity.User;
import com.pmc.market.model.vo.OrderResponseVo;
import com.pmc.market.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {OrderApplication.class})
class OrderServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderService orderService;

    @DisplayName("주문하기")
    @Test
    @WithMockUser
    void makeOrder() {
        List<ProductRequestDto> products = new ArrayList<>();
        products.add(ProductRequestDto.builder()
                .productId(1L)
                .size("1")
                .totalPrice(13000)
                .totalQuantity(1)
                .build());
        OrderRequestDto request = OrderRequestDto.builder()
                .pay(Pay.BANK)
                .shipAddress("서울 광진구 화양동 00 아파트 11번지 11호")
                .zipCode("04231")
                .totalProductPrice(13000)
                .shipCost(3000)
                .totalPrice(16000)
                .phoneNumber("010-1234-1234")
                .name("수취인 이름")
                .shopId(1L)
                .products(products)
                .amount(1)
                .userId(1L)
                .build();

        orderService.makeOrder(request);
    }


    @DisplayName("주문내역")
    @Test
    @Transactional
    void getUserOrderList() {
        User user = userRepository.findById(1L).get();

        List<OrderResponseVo> list = orderService.getUserOrderList(user);

        assertTrue(list.size() > 0);
    }

    @DisplayName("단일 조회")
    @Test
    @Transactional
    void findOrderById() {
        OrderResponseVo responseDto = orderService.getOrder(1L);
        assertEquals(responseDto.getPurchaseId(), 1L);
    }
}