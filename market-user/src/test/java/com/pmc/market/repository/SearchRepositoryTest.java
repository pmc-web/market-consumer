package com.pmc.market.repository;

import com.pmc.market.UserApplication;
import com.pmc.market.model.user.entity.Search;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {UserApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SearchRepositoryTest {

    @Autowired
    private SearchRepository searchRepository;

    @DisplayName("인기 검색어 - count 기준 정렬 ")
    @Test
    void findBySearchesOrderByCountDesc() {

        int limit = 10;
        List<Search> search = searchRepository.findBySearchesOrderByCountDesc(PageRequest.of(0, limit));

        search.stream().forEach(s -> {
            System.out.println(s.getWord() + " " + s.getCount());
        });
    }

    @DisplayName("count 개 이하 일때 ")
    @Test
    void findByCount() {
        int count = 0;
        List<Long> ids = searchRepository.findByCount(count);
        System.out.println(ids);
    }

    //    @Transactional
    @Test
    void deleteAllByIdInQuery() {
        List<Long> ids = searchRepository.findByCount(0);
        searchRepository.deleteAllByIdInQuery(ids);

    }
}