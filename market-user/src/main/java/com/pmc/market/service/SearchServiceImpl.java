package com.pmc.market.service;

import com.pmc.market.domain.user.dto.SearchResponseDto;
import com.pmc.market.domain.user.entity.Search;
import com.pmc.market.domain.user.repository.SearchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Slf4j
@RequiredArgsConstructor
@Service
public class SearchServiceImpl implements SearchService {

    private static final int DELETE_YEAR_BASE = 1;
    private final SearchRepository searchRepository;

    @Value("${project.batch.search-delete:0}")
    private int count;


    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul")
    public void batchDelete() {
        log.info("batchDelete TASK WORKING ---------");
        searchRepository.deleteAllByDate(LocalDateTime.now().minusYears(DELETE_YEAR_BASE));
    }

    @Transactional(readOnly = true)
    @Override
    public List<SearchResponseDto> getPopularList(long daysAgo, int limit) {
        return searchRepository.findPopularKeyword(LocalDateTime.now().minusDays(daysAgo), limit)
                .stream().map(s -> SearchResponseDto.of(((BigInteger) s[0]).longValue(), (String) s[1])).collect(Collectors.toList());
    }

    @Override
    public void addSearchList(String word) {
        Search search = Search.builder()
                .keyword(word)
                .regDate(LocalDateTime.now())
                .build();
        searchRepository.save(search);
    }
}
