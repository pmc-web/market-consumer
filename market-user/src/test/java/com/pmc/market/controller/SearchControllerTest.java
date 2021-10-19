package com.pmc.market.controller;

import com.pmc.market.UserApplication;
import com.pmc.market.model.dto.SearchResponseDto;
import com.pmc.market.service.SearchService;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {UserApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class SearchControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    SearchService searchService;

    @WithMockUser
    @DisplayName("검색어 추가")
    @Test
    void addSearchKeyword() throws Exception {
        String word = "검색";
        doNothing().when(searchService).addSearchList(word);
        mockMvc.perform(MockMvcRequestBuilders.post("/search")
                .param("keyword", word)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @WithMockUser
    @DisplayName("인기 검색어 리스트")
    @Test
    void getPopularSearchKeyword() throws Exception {
        long days = 7L;
        int limit = 10;
        List<SearchResponseDto> searchResponseDtos = new ArrayList<>();
        searchResponseDtos.add(SearchResponseDto.of(1, "검색"));
        when(searchService.getPopularList(days, limit)).thenReturn(searchResponseDtos);

        mockMvc.perform(MockMvcRequestBuilders.get("/search/popular")
                .param("daysAgo", String.valueOf(days))
                .param("limit", String.valueOf(limit))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }
}