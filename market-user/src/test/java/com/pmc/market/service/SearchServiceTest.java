package com.pmc.market.service;

import com.pmc.market.UserApplication;
import com.pmc.market.model.dto.SearchResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {UserApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SearchServiceTest {

    @Autowired
    SearchService searchService;

    @DisplayName("인기검색어 리스트")
    @Test
    void getPopularList() {
        long days = 7L;
        int limit = 10;
        List<SearchResponseDto> responseDtos = searchService.getPopularList(days, limit);
        assertTrue(responseDtos.size() > 0);
    }

    @DisplayName("검색어 추가")
    @Test
    void addSearchList() {
        String keyword = "검색어1";
        searchService.addSearchList(keyword);
    }
}