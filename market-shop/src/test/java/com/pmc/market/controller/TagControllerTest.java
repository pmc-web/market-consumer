package com.pmc.market.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pmc.market.ShopApplication;
import com.pmc.market.domain.shop.dto.TagRequestDto;
import com.pmc.market.domain.shop.dto.TagResponseDto;
import com.pmc.market.service.TagService;
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
@SpringBootTest(classes = {ShopApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class TagControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TagService tagService;

    @WithMockUser
    @DisplayName("전체 태 목록 가져오기 - 20210531 통과")
    @Test
    void getAllTags() throws Exception {
        TagResponseDto tag = TagResponseDto.builder()
                .id(4L)
                .tagName("태그 이름")
                .build();
        List<TagResponseDto> tags = new ArrayList<>();
        tags.add(tag);

        when(tagService.findAll()).thenReturn(tags);

        mockMvc.perform(MockMvcRequestBuilders.get("/tags")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @WithMockUser
    @DisplayName("태그 추가 - 20210531 통과")
    @Test
    void makeTag() throws Exception {
        TagRequestDto tagRequestDto = TagRequestDto.builder()
                .name("태그 이름1")
                .build();
        doNothing().when(tagService).makeTag(tagRequestDto);

        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders.post("/tags")
                .content(objectMapper.writeValueAsString(tagRequestDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @WithMockUser
    @DisplayName("태그 삭제 - 20210531 통과")
    @Test
    void deleteTag() throws Exception {
        long id = 4L;
        doNothing().when(tagService).deleteTag(id);
        mockMvc.perform(MockMvcRequestBuilders.delete("/tags/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @WithMockUser
    @DisplayName("태그 검색 목록 - 20210531 통과")
    @Test
    void getTagsBySearch() throws Exception {
        TagResponseDto tag = TagResponseDto.builder()
                .id(4L)
                .tagName("태그 이름")
                .build();
        List<TagResponseDto> tags = new ArrayList<>();
        tags.add(tag);
        String searchWord = "태";
        when(tagService.findAll()).thenReturn(tags);

        mockMvc.perform(MockMvcRequestBuilders.get("/tags/search")
                .param("searchWord", String.valueOf(searchWord))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @WithMockUser
    @DisplayName("태그 이름 검색 - 20210601 통과")
    @Test
    void getTagsByName() throws Exception {
        TagResponseDto tag = TagResponseDto.builder()
                .id(4L)
                .tagName("태그 이름")
                .build();
        List<TagResponseDto> tags = new ArrayList<>();
        tags.add(tag);
        String tagName = "태그1";
        when(tagService.findAll()).thenReturn(tags);

        mockMvc.perform(MockMvcRequestBuilders.get("/tags/name")
                .param("tagName", String.valueOf(tagName))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @WithMockUser
    @DisplayName("태그 아이디 검색 - 20210601 통과")
    @Test
    void getTagsById() throws Exception {
        TagResponseDto tag = TagResponseDto.builder()
                .id(4L)
                .tagName("태그 이름")
                .build();
        List<TagResponseDto> tags = new ArrayList<>();
        tags.add(tag);
        long id = 1L;
        when(tagService.findAll()).thenReturn(tags);

        mockMvc.perform(MockMvcRequestBuilders.get("/tags/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }
}