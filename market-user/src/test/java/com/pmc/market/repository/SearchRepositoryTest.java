//package com.pmc.market.repository;
//
//import com.pmc.market.UserApplication;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//@ExtendWith(SpringExtension.class)
//@SpringBootTest(classes = {UserApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//class SearchRepositoryTest {
//
//    @Autowired
//    private SearchRepository searchRepository;
//
//    @DisplayName("인기검색어")
//    @Test
//    void popular() {
//        long days = 7L;
//        int limit = 1;
//        List<Object[]> result = searchRepository.findPopularKeyword(LocalDateTime.now().minusDays(days), limit);
//        result.stream().forEach(s -> System.out.println(s[0] + " " + s[1]));
//    }
//
//    @DisplayName("날짜 지정 삭제")
//    @Test
//    void deleteAllByDateAndIdInQuery() {
//        searchRepository.deleteAllByDate(LocalDateTime.now().minusDays(1));
//    }
//}