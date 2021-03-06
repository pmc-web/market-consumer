package com.pmc.market.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pmc.market.ShopApplication;
import com.pmc.market.model.PageRequest;
import com.pmc.market.model.dto.ShopRequestDto;
import com.pmc.market.model.dto.ShopResponseDto;
import com.pmc.market.model.shop.entity.Favorite;
import com.pmc.market.model.shop.entity.Shop;
import com.pmc.market.model.user.entity.Role;
import com.pmc.market.model.user.entity.User;
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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {ShopApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ShopControllerTest {

    @Autowired
    MockMvc mockMvc;
    PageRequest pageable = new PageRequest();
    @MockBean
    private ShopService shopService;

    @Test
    @WithMockUser
    void ?????????_?????????_????????????() throws Exception {
        List<ShopResponseDto> shops = new ArrayList<>();
        shops.add(ShopResponseDto.builder()
                .id(1L)
                .name("?????????1")
                .telephone("010-0000-0000")
                .businessName("?????????1")
                .fullDescription("????????? ??????")
                .owner("??????")
                .shortDescription("???????????? ?????????")
                .regDate(LocalDateTime.now())
                .period(LocalDateTime.now().plusYears(1))
                .businessNumber("00-000-000")
                .build());
        shops.add(ShopResponseDto.builder()
                .id(2L)
                .name("?????????2")
                .telephone("010-0000-0000")
                .businessName("?????????2")
                .fullDescription("????????? ??????2")
                .owner("??????2")
                .shortDescription("??????????????? ?????????")
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

    @DisplayName("makeShop() ?????????")
    @Test
    void ?????????_??????() throws Exception {
        ShopRequestDto shop = ShopRequestDto.builder()
                .name("?????????1")
                .telephone("010-0000-0000")
                .businessName("?????????1")
                .fullDescription("????????? ??????")
                .owner("owner@Email.com")
                .shortDescription("???????????? ?????????")
                .period(1) // ???????????? 1???
                .businessNumber("00-000-000")
                .build();
        doNothing().when(shopService).makeShop(shop, User.builder().role(Role.SELLER).email("annna0449@naver.com").build(), null);

        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(MockMvcRequestBuilders.post("/shops")
                .content(objectMapper.writeValueAsString(shop))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("exception handler ?????????")
    void ???????????????() throws Exception {
        ShopRequestDto shop = ShopRequestDto.builder()
                .name("?????????1")
                .telephone("010-0000-0000")
                .businessName("?????????1")
                .fullDescription("????????? ??????")
                .owner("??????")
                .shortDescription("???????????? ?????????")
                .period(1) // ???????????? 1???
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
    @DisplayName("?????? ?????? ?????? N???")
    void ?????????_?????????_like1() throws Exception {
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
        shops.add(ShopResponseDto.from(shop, 1));
        shops.add(ShopResponseDto.from(shop2, 1));
        shops.add(ShopResponseDto.from(shop3, 1));
        pageable.setSize(5);
        pageable.setPage(1);
        when(shopService.findFavorite(pageable)).thenReturn(shops);

        mockMvc.perform(MockMvcRequestBuilders.get("/shops/favorite")
                .param("count", String.valueOf(3))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(3)))
                .andDo(print());
    }

    @WithMockUser
    @Test
    @DisplayName("?????? ?????? N???")
    void ?????????_?????????_new() throws Exception {
        int page = 1;
        int size = 3;
        List<ShopResponseDto> shops = new ArrayList<>();
        for (int i = 0; i < size; i++) shops.add(ShopResponseDto.builder().id(i + 1).build());
        pageable.setSize(5);
        pageable.setPage(1);
        when(shopService.findNew(pageable)).thenReturn(shops);

        mockMvc.perform(MockMvcRequestBuilders.get("/shops/new")
                .param("pageNumber", String.valueOf(page))
                .param("pageSize", String.valueOf(size))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @WithMockUser
    @Test
    @DisplayName("?????? ?????? ?????? - 1 ")
    void ??????_1???_??????_??????????????????() throws Exception {

        ShopResponseDto shop = ShopResponseDto.from(Shop.builder().id(1L).build());
        long id = 1L;
        when(shopService.getShopById(id)).thenReturn(shop);

        mockMvc.perform(MockMvcRequestBuilders.get("/shops/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @WithMockUser
    @Test
    @DisplayName("?????? ???????????? ????????? ?????? ")
    void ???????????????_??????_?????????() throws Exception {
        long id = 1L;
        List<ShopResponseDto> shops = new ArrayList<>();
        for (int i = 0; i < 4; i++) shops.add(ShopResponseDto.builder().id(i + 1).build());
        when(shopService.getShopsByCategory(id)).thenReturn(shops);

        mockMvc.perform(MockMvcRequestBuilders.get("/shops/category")
                .param("id", String.valueOf(id))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @WithMockUser
    @DisplayName("?????? ?????? - ?????????")
    @Test
    void getShopsBySearch() throws Exception {
        // ????????? ?????? ?????? ??
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
    @DisplayName("?????? ?????? ??????")
    @Test
    void updateShop() throws Exception {
        long id = 6L;
        ShopRequestDto shop = ShopRequestDto.builder()
                .name("hi")
                .businessName("update shop")
                .build();
        doNothing().when(shopService).updateShop(shop, id, null);

        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders.post("/shops/{id}", id)
                .content(objectMapper.writeValueAsString(shop))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @WithMockUser
    @DisplayName("?????? ?????? ")
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

    @WithMockUser
    @DisplayName("?????? ?????????")
    @Test
    void likeShop() throws Exception {
        long id = 7L;
        doNothing().when(shopService).likeUpdateShop(id, User.builder().id(1L).build());
        mockMvc.perform(MockMvcRequestBuilders.patch("/shops/{id}/like", id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
