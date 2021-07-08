package com.pmc.market.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pmc.market.OrderApplication;
import com.pmc.market.model.dto.DeliveryUpdateRequestDto;
import com.pmc.market.model.order.entity.DeliveryStatus;
import com.pmc.market.service.DeliveryService;
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

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {OrderApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class DeliveryControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    DeliveryService deliveryService;

    ObjectMapper objectMapper = new ObjectMapper();

    @DisplayName("배송시작")
    @WithMockUser
    @Test
    void startDelivery() throws Exception {
        long orderId = 1L;
        doNothing().when(deliveryService).insertDelivery(orderId);
        mockMvc.perform(MockMvcRequestBuilders.post("/delivery")
                .content(objectMapper.writeValueAsString(orderId))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("배송상태 업데이트")
    @WithMockUser
    @Test
    void updateDeliveryStatus() throws Exception {
        long deliveryId = 1L;
        DeliveryUpdateRequestDto deliveryUpdateReq = DeliveryUpdateRequestDto.builder()
                .deliveryStatus(DeliveryStatus.SHIPPING)
                .shippingNumber("12345-12322-123123")
                .build();
        doNothing().when(deliveryService).updateStatus(deliveryId, deliveryUpdateReq);
        mockMvc.perform(MockMvcRequestBuilders.put("/delivery/{deliveryId}", deliveryId)
                .content(objectMapper.writeValueAsString(deliveryUpdateReq))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

    }
}