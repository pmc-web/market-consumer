package com.pmc.market.controller;

import com.pmc.market.ProductApplication;
import com.pmc.market.entity.Product;
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

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {ProductApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    private ProductService productService;

    @Test
    void 쇼핑몰_목록을_가져온다() throws Exception {
        List<Product> products = new ArrayList<>();
        products.add(Product.builder()
                .id(1L)
                .name("귀걸이")
                .price(1000)
                .amount(10)
                .description("귀걸이입니다.")
                .build());
        products.add(Product.builder()
                .id(2L)
                .name("걸이")
                .price(2000)
                .amount(20)
                .description("목걸이입니다.")
                .build());
        when(productService.findProducts()).thenReturn(products);

        mockMvc.perform(MockMvcRequestBuilders.get("/products/v2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andDo(print());
    }

}