package com.pmc.market.repository;

import com.pmc.market.UserApplication;
import com.pmc.market.domain.user.dto.SearchResponseDto;
import com.pmc.market.domain.user.entity.Search;
import com.pmc.market.domain.user.repository.SearchRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {UserApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SearchRepositoryTest {

    @Autowired
    private SearchRepository searchRepository;

    @BeforeEach
    void setUp() {
        for (int i = 0; i < 10; i++) {
            searchRepository.save(Search.builder()
                    .keyword(i + "keyword")
                    .regDate(LocalDateTime.now())
                    .build());
        }
        for (int i = 0; i < 10; i++) {
            searchRepository.save(Search.builder()
                    .keyword(i % 2 == 0 ? "2" : "1")
                    .regDate(LocalDateTime.now())
                    .build());
        }
    }

    @DisplayName("인기검색어")
    @Test
    void popular() {
        int limit = 10;
        LocalDateTime before = LocalDateTime.now().minusDays(1l);
        List<Object[]> result = searchRepository.findPopularKeyword(before, limit);
        result.forEach(s -> System.out.println(s[0] + " " + s[1]));

        List<SearchResponseDto> popular = searchRepository.findPopular(before, limit);
        for (SearchResponseDto searchResponseDto : popular) {
            System.out.println(searchResponseDto);
        }


    }

    @DisplayName("날짜 지정 삭제")
    @Test
    void deleteAllByDateAndIdInQuery() {
        searchRepository.deleteAllByDate(LocalDateTime.now().minusDays(1));
    }
}