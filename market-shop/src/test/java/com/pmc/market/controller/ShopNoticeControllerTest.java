package com.pmc.market.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pmc.market.ShopApplication;
import com.pmc.market.model.dto.NoticeRequestDto;
import com.pmc.market.model.dto.NoticeResponseDto;
import com.pmc.market.model.entity.Shop;
import com.pmc.market.model.entity.ShopNotice;
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

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {ShopApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ShopNoticeControllerTest {
    @Autowired
    MockMvc mockMvc;
    NoticeRequestDto noticeRequestDto = NoticeRequestDto.builder()
            .title("타이틀")
            .content("공지사항1")
            .build();
    ShopNotice notice = ShopNotice.builder()
            .id(1L)
            .title("타이틀")
            .content("공지사항1")
            .regDate(LocalDateTime.now())
            .shop(Shop.builder().build())
            .build();
    @MockBean
    private ShopService shopService;

    @WithMockUser
    @Test
    @DisplayName("마켓 공지사항 리스트 조회 ")
    void 마켓_공지사항_리스트() throws Exception {
        long id = 1L;
        List<ShopNotice> notices = new ArrayList<>();
        when(shopService.getNoticeList(id)).thenReturn(notices);

        mockMvc.perform(MockMvcRequestBuilders.get("/shops/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @WithMockUser
    @Test
    @DisplayName("마켓 공지사항 작성 ")
    void 마켓_공지사항_작성() throws Exception {
        long shopId = 1L;

        ObjectMapper objectMapper = new ObjectMapper();
        when(shopService.insertNotice(shopId, noticeRequestDto)).thenReturn(NoticeResponseDto.of(notice));
        mockMvc.perform(MockMvcRequestBuilders.post("/shops/{id}/notice", shopId)
                .content(objectMapper.writeValueAsString(noticeRequestDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @WithMockUser
    @Test
    @DisplayName("마켓 공지사항 상세")
    void 마켓_공지사항_단일조회() throws Exception {
        long id = 1L;
        when(shopService.getNotice(id)).thenReturn(NoticeResponseDto.of(notice));
        mockMvc.perform(MockMvcRequestBuilders.get("/shops/notice/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @WithMockUser
    @Test
    @DisplayName("마켓 공지사항 수정")
    void 마켓_공지사항_수정() throws Exception {
        long id = 1L;
        ObjectMapper objectMapper = new ObjectMapper();
        when(shopService.updateNotice(id, noticeRequestDto)).thenReturn(NoticeResponseDto.of(notice));
        mockMvc.perform(MockMvcRequestBuilders.put("/shops/notice/{id}", id)
                .content(objectMapper.writeValueAsString(noticeRequestDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @WithMockUser
    @Test
    @DisplayName("마켓 공지사항 삭제")
    void 마켓_공지사항_삭제() throws Exception {
        long id = 1L;
        doNothing().when(shopService).deleteNotice(id);
        mockMvc.perform(MockMvcRequestBuilders.delete("/shops/notice/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
