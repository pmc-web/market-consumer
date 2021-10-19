package com.pmc.market.controller;

import com.pmc.market.OrderApplication;
import com.pmc.market.model.order.entity.Pay;
import com.pmc.market.model.user.entity.User;
import com.pmc.market.model.vo.OrderResponseVo;
import com.pmc.market.service.OrderService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {OrderApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class OrderControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    OrderService orderService;

    @DisplayName("구매목록 -> authentication 없을 떄 통과")
    @WithMockUser
    @Test
    void getOrderList() throws Exception {
        User user = User.builder().build();
        List<OrderResponseVo> orderList = new ArrayList<>();
        orderList.add(OrderResponseVo
                .builder()
                .pay(Pay.KAKAO_PAY)
                .regDate(LocalDateTime.now())
                .purchaseId(1L)
                .build());
        when(orderService.getUserOrderList(user)).thenReturn(orderList);

        mockMvc.perform(MockMvcRequestBuilders.get("/orders")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("구매목록 -> authentication 없을 떄 통과")
    @WithMockUser
    @Test
    void getOrderById() throws Exception {
        when(orderService.getOrder(1L)).thenReturn(OrderResponseVo.builder().build());
        mockMvc.perform(MockMvcRequestBuilders.get("/orders/{orderId}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

}