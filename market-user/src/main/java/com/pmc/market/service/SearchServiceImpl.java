package com.pmc.market.service;

import com.pmc.market.entity.Search;
import com.pmc.market.repository.SearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SearchServiceImpl implements SearchService {

    private final SearchRepository searchRepository;

    @Value("${project.batch.search-delete:0}")
    private int count;

    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul")
    private void batchDelete() {
        // count 가 0 인것 삭제
        List<Long> ids = searchRepository.findByCount(count);
        searchRepository.deleteAllByIdInQuery(ids);
    }

    @Override
    public void getPopularList(int limit) {
        List<Search> searches = searchRepository.findBySearchesOrderByCountDesc(PageRequest.of(0, limit));
    }
}
