package com.pmc.market.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pmc.market.ShopApplication;
import com.pmc.market.entity.Role;
import com.pmc.market.entity.User;
import com.pmc.market.model.dto.ShopResponseDto;
import com.pmc.market.model.entity.Favorite;
import com.pmc.market.model.entity.Shop;
import com.pmc.market.model.dto.ShopRequestDto;
import com.pmc.market.service.ShopService;
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

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {ShopApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ShopControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private ShopService shopService;

    @Test
    @WithMockUser
    void 쇼핑몰_목록을_가져온다() throws Exception {
        List<ShopResponseDto> shops = new ArrayList<>();
        shops.add(ShopResponseDto.builder()
                .id(1L)
                .name("쇼핑몰1")
                .telephone("010-0000-0000")
                .businessName("쇼핑몰1")
                .fullDescription("쇼핑몰 설명")
                .owner("주인")
                .shortDescription("악세사리 쇼핑몰")
                .regDate(LocalDateTime.now())
                .period(LocalDateTime.now().plusYears(1))
                .businessNumber("00-000-000")
                .build());
        shops.add(ShopResponseDto.builder()
                .id(2L)
                .name("쇼핑몰2")
                .telephone("010-0000-0000")
                .businessName("쇼핑몰2")
                .fullDescription("쇼핑몰 설명2")
                .owner("주인2")
                .shortDescription("핸드메이드 쇼핑몰")
                .regDate(LocalDateTime.now())
                .period(LocalDateTime.now().plusYears(1))
                .businessNumber("00-000-000")
                .build());
        when(shopService.findAll()).thenReturn(shops);

        mockMvc.perform(MockMvcRequestBuilders.get("/shops")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andDo(print());
    }

    @DisplayName("makeShop() 테스트")
    @Test
    void 쇼핑몰_등록() throws Exception {
        ShopRequestDto shop = ShopRequestDto.builder()
                .name("쇼핑몰1")
                .telephone("010-0000-0000")
                .businessName("쇼핑몰1")
                .fullDescription("쇼핑몰 설명")
                .owner("owner@Email.com")
                .shortDescription("악세사리 쇼핑몰")
                .period(1) // 유지기간 1년
                .businessNumber("00-000-000")
                .build();
        doNothing().when(shopService).makeShop(shop, User.builder().role(Role.SELLER).email("annna0449@naver.com").build());

        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(MockMvcRequestBuilders.post("/shops")
                .content(objectMapper.writeValueAsString(shop))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("exception handler 테스트")
    void 유효성체크() throws Exception {
        ShopRequestDto shop = ShopRequestDto.builder()
                .name("쇼핑몰1")
                .telephone("010-0000-0000")
                .businessName("쇼핑몰1")
                .fullDescription("쇼핑몰 설명")
                .owner("주인")
                .shortDescription("악세사리 쇼핑몰")
                .period(1) // 유지기간 1년
                .businessNumber(null)
                .build();
        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(MockMvcRequestBuilders.post("/shops")
                .content(objectMapper.writeValueAsString(shop))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }

    @WithMockUser
    @Test
    @DisplayName("요즘 뜨는 마켓 N개")
    void 쇼핑몰_리스트_like1() throws Exception {
        // favorite table
        Shop shop = Shop.builder()
                .id(1L)
                .name("shop1")
                .build();
        Shop shop2 = Shop.builder()
                .id(2L)
                .name("shop2")
                .build();
        Shop shop3 = Shop.builder()
                .id(3L)
                .name("shop3")
                .build();
        User user = User.builder()
                .id(1L)
                .email("annna0449@naver.com")
                .password("password123$")
                .role(Role.BUYER)
                .build();
        Favorite.builder()
                .id(1L)
                .shop(shop)
                .user(user)
                .build();
        Favorite.builder()
                .id(2L)
                .shop(shop2)
                .user(user)
                .build();
        Favorite.builder()
                .id(3L)
                .shop(shop3)
                .user(user)
                .build();
        List<ShopResponseDto> shops = new ArrayList<>();
        shops.add(ShopResponseDto.of(shop, 1));
        shops.add(ShopResponseDto.of(shop2, 1));
        shops.add(ShopResponseDto.of(shop3, 1));

        when(shopService.findFavorite(3)).thenReturn(shops);

        mockMvc.perform(MockMvcRequestBuilders.get("/shops/favorite")
                .param("count", String.valueOf(3))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(3)))
                .andDo(print());
    }

    @WithMockUser
    @Test
    @DisplayName("신상 마켓 N개")
    void 쇼핑몰_리스트_new() throws Exception {
        int count = 6;
        List<ShopResponseDto> shops = new ArrayList<>();
        for (int i = 0; i < count; i++) shops.add(ShopResponseDto.builder().id(i+1).build());
        when(shopService.findNew(count)).thenReturn(shops);

        mockMvc.perform(MockMvcRequestBuilders.get("/shops/new")
                .param("count", String.valueOf(count))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @WithMockUser
    @Test
    @DisplayName("마켓 정보 조회 - 1 ")
    void 마켓_1개_조회_좋아요수포함() throws Exception{

        ShopResponseDto shop = ShopResponseDto.of(Shop.builder().id(1L).build());
        long id = 1L;
        when(shopService.getShopById(id)).thenReturn(shop);

        mockMvc.perform(MockMvcRequestBuilders.get("/shops/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @WithMockUser
    @Test
    @DisplayName("마켓 카테고리 리스트 조회 ")
    void 카테고리별_마켓_리스트() throws Exception{
        long id = 1L;
        List<ShopResponseDto> shops = new ArrayList<>();
        for(int i=0; i<4; i++) shops.add(ShopResponseDto.builder().id(i+1).build());
        when(shopService.getShopsByCategory(id)).thenReturn(shops);

        mockMvc.perform(MockMvcRequestBuilders.get("/shops/category")
                .param("id", String.valueOf(id))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @WithMockUser
    @DisplayName("마켓 조회 - 검색어")
    @Test
    void getShopsBySearch() throws Exception {
        // 쇼핑몰 이름 검색 ??
        String searchWord = "213";
        List<ShopResponseDto> shops = new ArrayList<>();

        when(shopService.getShopsBySearch(searchWord)).thenReturn(shops);

        mockMvc.perform(MockMvcRequestBuilders.get("/shops/search")
                .param("searchWord", String.valueOf(searchWord))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @WithMockUser
    @DisplayName("마켓 정보 수정")
    @Test
    void updateShop() throws Exception {
        long id = 6L;
        ShopRequestDto shop = ShopRequestDto.builder()
                .name("hi")
                .businessName("update shop")
                .build();
        doNothing().when(shopService).updateShop(shop, id);

        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders.post("/shops/{id}", id)
                .content(objectMapper.writeValueAsString(shop))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @WithMockUser
    @DisplayName("마켓 삭제 ")
    @Test
    void deleteShop() throws Exception {
        long id = 7L;
        doNothing().when(shopService).deleteShop(id);
        mockMvc.perform(MockMvcRequestBuilders.post("/shops/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }



}
