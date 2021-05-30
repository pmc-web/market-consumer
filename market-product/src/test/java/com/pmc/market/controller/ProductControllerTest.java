package com.pmc.market.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pmc.market.ProductApplication;
import com.pmc.market.model.entity.Product;
import com.pmc.market.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = {ProductApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    private ProductService productService;

    @Test
    public void 상품_등록() throws Exception {
        //given 이런게 주어졌을 때
        Product products = Product.builder()
                .id(1L)
                .name("귀걸이")
                .price(1000)
                .amount(10)
                .description("귀걸이입니다.")
                .build();

        //when 이렇게 하면
        doNothing().when(productService).saveProduct(products);

        ObjectMapper objectMapper = new ObjectMapper();

        //then 이렇게 된다.
        mockMvc.perform(MockMvcRequestBuilders.post("/products/save")
                .content(objectMapper.writeValueAsString(products))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }
}